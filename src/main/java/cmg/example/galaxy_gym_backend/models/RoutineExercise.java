package cmg.example.galaxy_gym_backend.models;


import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "routine_exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "routine_id", nullable = false)
    private WorkoutRoutine routine;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Min(1)
    private Integer sets;

    @Min(1)
    private Integer reps;

    private Integer restSeconds;

    @DecimalMin("0.0")
    private Double weight;

    private String duration; // Para ejercicios cardiovasculares

    private String notes;

    private Integer orderIndex;

    @Enumerated(EnumType.STRING)
    private IntensityLevel intensity;

    public enum IntensityLevel {
        LOW, MEDIUM, HIGH, MAXIMUM
    }
}