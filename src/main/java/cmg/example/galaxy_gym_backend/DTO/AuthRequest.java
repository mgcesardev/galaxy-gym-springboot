package cmg.example.galaxy_gym_backend.DTO;

import lombok.Data;

@Data
public class AuthRequest {
    private String correoElectronico;
    private String contrasena;
}
