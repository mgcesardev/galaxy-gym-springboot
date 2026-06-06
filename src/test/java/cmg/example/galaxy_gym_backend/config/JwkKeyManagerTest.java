package cmg.example.galaxy_gym_backend.config;

import org.junit.jupiter.api.Test;
import java.security.interfaces.RSAPublicKey;
import static org.junit.jupiter.api.Assertions.*;

class JwkKeyManagerTest {

    @Test
    void testKeyManagerInitialization() {
        JwkKeyManager keyManager = new JwkKeyManager();
        
        assertNotNull(keyManager.getRsaKey());
        assertNotNull(keyManager.getKeyId());
        
        RSAPublicKey publicKey = keyManager.getPublicKey();
        assertNotNull(publicKey);
        assertEquals("RSA", publicKey.getAlgorithm());
    }
}
