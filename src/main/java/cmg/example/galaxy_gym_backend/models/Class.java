package cmg.example.galaxy_gym_backend.models;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import cmg.example.galaxy_gym_backend.models.Exercise.DifficultyLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "classes")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Enumerated(EnumType.STRING)
    private ClassType type;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer maxCapacity;

    private Integer currentEnrollment = 0;

    private String location;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;

    @DecimalMin("0.0")
    private Double price;

    private Boolean isRecurring = false;

    private String recurrencePattern;

    @Enumerated(EnumType.STRING)
    private ClassStatus status = ClassStatus.SCHEDULED;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
    private Set<Reservation> reservations;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum ClassType {
        YOGA, PILATES, SPINNING, ZUMBA, CROSSFIT,
        HIIT, BOXING, DANCE, STRETCHING, MEDITATION,
        AEROBICS, BODY_PUMP, TRX, KETTLEBELL
    }

    public enum ClassStatus {
        SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED, FULL
    }
}