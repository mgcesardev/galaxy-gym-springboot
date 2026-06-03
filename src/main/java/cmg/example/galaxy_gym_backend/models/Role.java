package cmg.example.galaxy_gym_backend.models;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoleType name;

    private String description;

    public enum RoleType {
        ROLE_USER,
        ROLE_ADMIN,
        ROLE_TRAINER,
        ROLE_STAFF
    }
}