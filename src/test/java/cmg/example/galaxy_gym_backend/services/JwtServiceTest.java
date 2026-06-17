package cmg.example.galaxy_gym_backend.services;

import cmg.example.galaxy_gym_backend.config.JwkKeyManager;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    private JwkKeyManager jwkKeyManager;
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwkKeyManager = new JwkKeyManager();
        jwtService = new JwtService(jwkKeyManager);
        ReflectionTestUtils.setField(jwtService, "jwtExpirationSeconds", 3600);
    }

    @Test
    void testGenerateToken() throws Exception {
        String email = "test@example.com";
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        String token = jwtService.generateToken(email, roles);
        assertNotNull(token);

        SignedJWT signedJWT = SignedJWT.parse(token);
        assertEquals("galaxy-gym", signedJWT.getJWTClaimsSet().getIssuer());
        assertEquals(email, signedJWT.getJWTClaimsSet().getSubject());
        
        List<String> tokenRoles = signedJWT.getJWTClaimsSet().getStringListClaim("roles");
        assertEquals(2, tokenRoles.size());
        assertTrue(tokenRoles.contains("ROLE_USER"));
    }

    @Test
    void testGenerateTokenThrowsRuntimeExceptionOnJoseException() throws Exception {
        JwkKeyManager mockJwkKeyManager = Mockito.mock(JwkKeyManager.class);
        RSAKey mockRsaKey = Mockito.mock(RSAKey.class);

        when(mockJwkKeyManager.getRsaKey()).thenReturn(mockRsaKey);
        when(mockRsaKey.toRSAPrivateKey()).thenThrow(new JOSEException("Signature failed"));

        JwtService jwtServiceWithMock = new JwtService(mockJwkKeyManager);
        ReflectionTestUtils.setField(jwtServiceWithMock, "jwtExpirationSeconds", 3600);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jwtServiceWithMock.generateToken("test@example.com", Arrays.asList("ROLE_USER"));
        });

        assertTrue(exception.getMessage().contains("Error generating asymmetric JWT token"));
    }
}
