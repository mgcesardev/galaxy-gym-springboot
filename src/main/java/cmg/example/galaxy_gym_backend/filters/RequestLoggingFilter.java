package cmg.example.galaxy_gym_backend.filters;

import cmg.example.galaxy_gym_backend.config.RateLimitProperties;
import cmg.example.galaxy_gym_backend.exceptions.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private final ObjectMapper objectMapper;
    private final RateLimitProperties rateLimitProperties;
    private final ConcurrentHashMap<String, Bucket> cache = new ConcurrentHashMap<>();

    public RequestLoggingFilter(RateLimitProperties rateLimitProperties) {
        this.rateLimitProperties = rateLimitProperties;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    private Bucket createNewBucket() {
        long maxRequests = rateLimitProperties.getMaxRequests();
        long windowSeconds = rateLimitProperties.getWindowSeconds();
        
        Bandwidth limit = Bandwidth.builder()
                .capacity(maxRequests)
                .refillIntervally(maxRequests, Duration.ofSeconds(windowSeconds))
                .build();
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String ipAddress = getClientIp(request);
        Bucket bucket = cache.computeIfAbsent(ipAddress, k -> createNewBucket());

        long maxRequests = rateLimitProperties.getMaxRequests();

        // Add standard rate limit headers to all responses
        response.setHeader("X-RateLimit-Limit", String.valueOf(maxRequests));
        
        // Try to consume 1 token
        if (!bucket.tryConsume(1)) {
            log.warn("Rate limit exceeded for IP: {} - Too many requests", ipAddress);
            
            // Calculate wait time in seconds for the Retry-After header
            long nanosToWait = bucket.estimateAbilityToConsume(1).getNanosToWaitForRefill();
            long secondsToWait = (long) Math.ceil(nanosToWait / 1_000_000_000.0);
            response.setHeader("Retry-After", String.valueOf(secondsToWait));
            response.setHeader("X-RateLimit-Remaining", "0");
            
            handleRateLimitExceeded(request, response);
            return;
        }

        // Add remaining tokens header if request is allowed
        response.setHeader("X-RateLimit-Remaining", String.valueOf(bucket.getAvailableTokens()));

        // Generate trace ID
        String traceId = request.getHeader("X-Trace-Id");
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString();
        }
        
        response.setHeader("X-Trace-Id", traceId);
        long startTime = System.currentTimeMillis();

        try {
            log.info("Incoming Request [TraceID: {}] [IP: {}] : {} {}", traceId, ipAddress, request.getMethod(), request.getRequestURI());
            
            filterChain.doFilter(request, response);
            
            long duration = System.currentTimeMillis() - startTime;
            log.info("Outgoing Response [TraceID: {}] : Status {} in {} ms", traceId, response.getStatus(), duration);

        } catch (Exception ex) {
            log.error("Exception caught in RequestLoggingFilter [TraceID: {}]: ", traceId, ex);
            handleFilterException(request, response, ex);
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isBlank()) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }

    private void handleRateLimitExceeded(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
            LocalDateTime.now(),
            HttpStatus.TOO_MANY_REQUESTS.value(),
            HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(),
            "Límite de solicitudes excedido. Por favor, intente más tarde.",
            request.getRequestURI()
        );

        String jsonResponse = objectMapper.writeValueAsString(apiErrorResponse);
        response.getWriter().write(jsonResponse);
    }

    private void handleFilterException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            "Ocurrió un error inesperado al procesar la solicitud en el filtro.",
            request.getRequestURI()
        );

        String jsonResponse = objectMapper.writeValueAsString(apiErrorResponse);
        response.getWriter().write(jsonResponse);
    }
}
