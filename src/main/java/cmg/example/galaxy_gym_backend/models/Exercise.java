package cmg.example.galaxy_gym_backend.models;

import lombok.*;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "exercises")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private MuscleGroup primaryMuscle;

    @Enumerated(EnumType.STRING)
    private MuscleGroup secondaryMuscle;

    @Enumerated(EnumType.STRING)
    private ExerciseType type;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;

    private String videoUrl;

    private String imageUrl;

    private Integer caloriesBurnedPerMinute;

    private Boolean requiresEquipment = false;

    private String instructions;

    private String safetyTips;

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

    public enum MuscleGroup {
        CHEST, BACK, SHOULDERS, BICEPS, TRICEPS, 
        FOREARMS, ABS, OBLIQUES, QUADRICEPS, 
        HAMSTRINGS, GLUTES, CALVES, FULL_BODY
    }

    public enum ExerciseType {
        STRENGTH, CARDIO, FLEXIBILITY, BALANCE, 
        PLYOMETRIC, BODYWEIGHT, CROSSFIT, YOGA
    }

    public enum DifficultyLevel {
        BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
    }
}