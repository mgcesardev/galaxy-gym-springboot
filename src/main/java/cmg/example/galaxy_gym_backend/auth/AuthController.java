package cmg.example.galaxy_gym_backend.auth;

import cmg.example.galaxy_gym_backend.DTO.AuthRequest;
import cmg.example.galaxy_gym_backend.DTO.AuthResponse;
import cmg.example.galaxy_gym_backend.DTO.GoogleAuthRequest;
import cmg.example.galaxy_gym_backend.models.Role;
import cmg.example.galaxy_gym_backend.models.User;
import cmg.example.galaxy_gym_backend.repositories.RoleRepository;
import cmg.example.galaxy_gym_backend.repositories.UserRepository;
import cmg.example.galaxy_gym_backend.services.JwtService;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    @Value("${app.google.client-id}")
    private String googleClientId;

    public AuthController(UserRepository userRepository, RoleRepository roleRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Optional<User> userOpt = userRepository.findByEmail(authRequest.getCorreoElectronico());

            if (userOpt.isPresent()) {
                User user = userOpt.get();

                // Verify password and active status
                if (BCrypt.checkpw(authRequest.getContrasena(), user.getPassword()) && Boolean.TRUE.equals(user.getIsActive())) {
                    List<String> roles = user.getRoles().stream()
                            .map(role -> role.getName().name())
                            .collect(Collectors.toList());

                    String token = jwtService.generateToken(user.getEmail(), roles);
                    return ResponseEntity.ok(new AuthResponse(token, user));
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas o usuario inactivo.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el microservicio de autenticación: " + e.getMessage());
        }
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody GoogleAuthRequest googleAuthRequest) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(googleAuthRequest.idToken());
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de Google inválido.");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            // Find or create the user
            Optional<User> userOpt = userRepository.findByEmail(email);
            User user;

            if (userOpt.isPresent()) {
                user = userOpt.get();
                if (!Boolean.TRUE.equals(user.getIsActive())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario está inactivo.");
                }
            } else {
                // Register a new user
                user = new User();
                user.setEmail(email);
                user.setUsername(email.split("@")[0] + "_" + UUID.randomUUID().toString().substring(0, 4));
                user.setFirstName((String) payload.get("given_name"));
                user.setLastName((String) payload.get("family_name"));
                
                // Password is required, so generate a secure random string and hash it
                String randomPassword = UUID.randomUUID().toString();
                user.setPassword(BCrypt.hashpw(randomPassword, BCrypt.gensalt()));
                
                user.setIsActive(true);
                user.setIsVerified(true); // Google verified emails are trusted

                // Assign default role (ROLE_USER or ROLE_MEMBER)
                Optional<Role> defaultRole = roleRepository.findByName(Role.RoleType.ROLE_USER);
                if (defaultRole.isEmpty()) {
                    defaultRole = roleRepository.findByName(Role.RoleType.ROLE_MEMBER);
                }
                
                if (defaultRole.isPresent()) {
                    user.setRoles(new HashSet<>(Collections.singletonList(defaultRole.get())));
                }

                user = userRepository.save(user);
            }

            // Generate backend JWT for authenticated user
            List<String> roles = user.getRoles().stream()
                    .map(role -> role.getName().name())
                    .collect(Collectors.toList());

            String token = jwtService.generateToken(user.getEmail(), roles);
            return ResponseEntity.ok(new AuthResponse(token, user));

        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al validar autenticación con Google: " + e.getMessage());
        }
    }
}
