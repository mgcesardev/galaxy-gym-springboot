package cmg.example.galaxy_gym_backend.models;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

import cmg.example.galaxy_gym_backend.models.Exercise.DifficultyLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "workout_routines")
@NoArgsConstructor
@SuppressWarnings("unused")
public class WorkoutRoutine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Enumerated(EnumType.STRING)
    private RoutineType type;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;

    private Integer durationMinutes;

    private Integer estimatedCaloriesBurn;

    private Integer restBetweenSetsSeconds;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoutineExercise> exercises;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private Boolean isPublic = false;

    private Boolean isTemplate = false;

    private Integer popularity;

    private Double rating;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        popularity = 0;
        rating = 0.0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum RoutineType {
        STRENGTH_TRAINING, HIIT, CARDIO, FLEXIBILITY,
        CIRCUIT_TRAINING, SPLIT_TRAINING, FULL_BODY,
        PUSH_PULL_LEGS, UPPER_LOWER, CUSTOM
    }
}