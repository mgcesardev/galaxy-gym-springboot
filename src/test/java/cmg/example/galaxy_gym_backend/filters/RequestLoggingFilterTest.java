package cmg.example.galaxy_gym_backend.filters;

import cmg.example.galaxy_gym_backend.config.RateLimitProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestLoggingFilterTest {

    private RateLimitProperties rateLimitProperties;
    private RequestLoggingFilter filter;

    @BeforeEach
    void setUp() {
        rateLimitProperties = new RateLimitProperties();
        rateLimitProperties.setMaxRequests(2); // Set maxRequests to 2 for easier rate limit testing
        rateLimitProperties.setWindowSeconds(10);
        filter = new RequestLoggingFilter(rateLimitProperties);
    }

    @Test
    void testStandardRequestFlowWithNewTraceId() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
        request.setRemoteAddr("127.0.0.1");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        filter.doFilter(request, response, filterChain);

        // Verify rate limit headers
        assertEquals("2", response.getHeader("X-RateLimit-Limit"));
        assertEquals("1", response.getHeader("X-RateLimit-Remaining"));

        // Verify Trace ID was generated and set
        String traceId = response.getHeader("X-Trace-Id");
        assertNotNull(traceId);
        assertFalse(traceId.isBlank());
    }

    @Test
    void testStandardRequestFlowWithExistingTraceId() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
        request.setRemoteAddr("127.0.0.1");
        request.addHeader("X-Trace-Id", "existing-trace-id-123");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        filter.doFilter(request, response, filterChain);

        // Verify trace ID is reused
        assertEquals("existing-trace-id-123", response.getHeader("X-Trace-Id"));
    }

    @Test
    void testClientIpFromXForwardedForSingleIp() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
        request.addHeader("X-Forwarded-For", "203.0.113.195");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        filter.doFilter(request, response, filterChain);

        // Remains successful, but uses X-Forwarded-For IP
        assertEquals("2", response.getHeader("X-RateLimit-Limit"));
        assertEquals("1", response.getHeader("X-RateLimit-Remaining"));
    }

    @Test
    void testClientIpFromXForwardedForMultipleIps() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
        request.addHeader("X-Forwarded-For", " 203.0.113.195, 70.41.3.18, 150.172.238.178 ");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        filter.doFilter(request, response, filterChain);

        assertEquals("2", response.getHeader("X-RateLimit-Limit"));
        assertEquals("1", response.getHeader("X-RateLimit-Remaining"));
    }

    @Test
    void testRateLimitExceeded() throws ServletException, IOException {
        // Max requests is 2
        String ip = "192.168.1.10";

        // Request 1: Allowed
        MockHttpServletRequest req1 = new MockHttpServletRequest("GET", "/api/test");
        req1.setRemoteAddr(ip);
        MockHttpServletResponse res1 = new MockHttpServletResponse();
        filter.doFilter(req1, res1, new MockFilterChain());
        assertEquals("1", res1.getHeader("X-RateLimit-Remaining"));
        assertEquals(HttpStatus.OK.value(), res1.getStatus());

        // Request 2: Allowed
        MockHttpServletRequest req2 = new MockHttpServletRequest("GET", "/api/test");
        req2.setRemoteAddr(ip);
        MockHttpServletResponse res2 = new MockHttpServletResponse();
        filter.doFilter(req2, res2, new MockFilterChain());
        assertEquals("0", res2.getHeader("X-RateLimit-Remaining"));
        assertEquals(HttpStatus.OK.value(), res2.getStatus());

        // Request 3: Blocked (Rate Limit Exceeded)
        MockHttpServletRequest req3 = new MockHttpServletRequest("GET", "/api/test");
        req3.setRemoteAddr(ip);
        MockHttpServletResponse res3 = new MockHttpServletResponse();
        filter.doFilter(req3, res3, new MockFilterChain());

        assertEquals("0", res3.getHeader("X-RateLimit-Remaining"));
        assertNotNull(res3.getHeader("Retry-After"));
        assertEquals(HttpStatus.TOO_MANY_REQUESTS.value(), res3.getStatus());
        assertTrue(res3.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE));
        assertTrue(res3.getContentAsString().contains("L\u00edmite de solicitudes excedido"));
    }

    @Test
    void testFilterChainExceptionHandling() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/exception");
        request.setRemoteAddr("127.0.0.1");
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Custom FilterChain that throws an exception
        FilterChain exceptionChain = (req, res) -> {
            throw new RuntimeException("Simulated filter chain failure");
        };

        filter.doFilter(request, response, exceptionChain);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertTrue(response.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE));
        assertTrue(response.getContentAsString().contains("Ocurri\u00f3 un error inesperado al procesar la solicitud en el filtro"));
    }
}
