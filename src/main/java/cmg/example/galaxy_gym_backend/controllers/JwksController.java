package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.config.JwkKeyManager;
import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JwksController {

    private final JwkKeyManager jwkKeyManager;

    public JwksController(JwkKeyManager jwkKeyManager) {
        this.jwkKeyManager = jwkKeyManager;
    }

    @GetMapping("/auth/jwks.json")
    public Map<String, Object> getJwkSet() {
        JWKSet jwkSet = new JWKSet(jwkKeyManager.getRsaKey().toPublicJWK());
        return jwkSet.toJSONObject();
    }
}
