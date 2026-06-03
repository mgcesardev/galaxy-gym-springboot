package cmg.example.galaxy_gym_backend.models;

import lombok.Data;
import java.util.Set;

@Data
public class AdminUserUpdateDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private Set<String> roles;
    private Boolean isActive;
    private Boolean accountLocked;
}
