
package cmg.example.galaxy_gym_backend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import cmg.example.galaxy_gym_backend.DTO.AuthRequest;
import cmg.example.galaxy_gym_backend.models.User;
import cmg.example.galaxy_gym_backend.repositories.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Optional<User> userOpt = userRepository.findByEmail(authRequest.getCorreoElectronico());

            if (userOpt.isPresent()) {
                User user = userOpt.get();

                // Verificar la contraseña con BCrypt y que el usuario esté activo
                if (BCrypt.checkpw(authRequest.getContrasena(), user.getPassword()) && Boolean.TRUE.equals(user.getIsActive())) {
                    return ResponseEntity.ok(user);
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas o usuario inactivo.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el microservicio de autenticación: " + e.getMessage());
        }
    }
}
