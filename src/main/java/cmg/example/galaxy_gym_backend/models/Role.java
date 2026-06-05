package cmg.example.galaxy_gym_backend.models;

import lombok.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
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
        ROLE_STAFF, ROLE_MEMBER, ROLE_RECEPTIONIST, ROLE_NUTRITIONIST, ROLE_PHYSICAL_THERAPIST, ROLE_MANAGER
    }
}