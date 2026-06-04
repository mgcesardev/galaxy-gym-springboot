package cmg.example.galaxy_gym_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "trainers")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String specialization;

    @Column(columnDefinition = "TEXT")
    private String certifications;

    private Integer yearsOfExperience;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @DecimalMin("0.0")
    private BigDecimal hourlyRate;

    @Enumerated(EnumType.STRING)
    private TrainerStatus status = TrainerStatus.ACTIVE;

    private String schedule;

    private Integer maxClients;

    private Double rating;

    private String profilePicture;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "trainer")
    private Set<GymClass> classes;

    @OneToMany(mappedBy = "trainer")
    private Set<WorkoutRoutine> routines;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum TrainerStatus {
        ACTIVE, INACTIVE, ON_LEAVE, TERMINATED
    }
}
