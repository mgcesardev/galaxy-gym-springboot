package cmg.example.galaxy_gym_backend.services;

import cmg.example.galaxy_gym_backend.config.JwkKeyManager;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private final JwkKeyManager jwkKeyManager;

    @Value("${app.security.jwt.expiration-seconds:86400}")
    private long jwtExpirationSeconds;

    public JwtService(JwkKeyManager jwkKeyManager) {
        this.jwkKeyManager = jwkKeyManager;
    }

    public String generateToken(String email, List<String> roles) {
        try {
            JWSSigner signer = new RSASSASigner(jwkKeyManager.getRsaKey().toRSAPrivateKey());

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(email)
                    .issuer("galaxy-gym")
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plusSeconds(jwtExpirationSeconds)))
                    .claim("roles", roles)
                    .build();

            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                    .keyID(jwkKeyManager.getKeyId())
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claimsSet);
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error generating asymmetric JWT token", e);
        }
    }
}
