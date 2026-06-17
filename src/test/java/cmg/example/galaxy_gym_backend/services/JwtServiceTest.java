package cmg.example.galaxy_gym_backend.services;

import cmg.example.galaxy_gym_backend.config.JwkKeyManager;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}
