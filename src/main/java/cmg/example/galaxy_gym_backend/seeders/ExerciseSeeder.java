package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import cmg.example.galaxy_gym_backend.models.Exercise;
import cmg.example.galaxy_gym_backend.models.Exercise.DifficultyLevel;
import cmg.example.galaxy_gym_backend.models.Exercise.ExerciseType;
import cmg.example.galaxy_gym_backend.models.Exercise.MuscleGroup;
import cmg.example.galaxy_gym_backend.repositories.ExerciseRepository;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@Order(2)
public class ExerciseSeeder implements CommandLineRunner {

    private final ExerciseRepository exerciseRepository;

    public ExerciseSeeder(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (exerciseRepository.count() == 0) {
            log.info("Seeding initial exercises...");
            List<Exercise> exercises = Arrays.asList(
                new Exercise(null, "Press de Banca", "Ejercicio clásico para pectoral en banco plano", MuscleGroup.CHEST, MuscleGroup.TRICEPS, ExerciseType.STRENGTH, DifficultyLevel.BEGINNER, "https://example.com/video/bench-press", "https://example.com/img/bench-press", 8, true, "Acuéstate en el banco, baja la barra al pecho y empuja.", "Usa un spotter para pesos pesados.", null, null),
                new Exercise(null, "Sentadillas libres", "Sentadillas profundas con barra", MuscleGroup.QUADRICEPS, MuscleGroup.GLUTES, ExerciseType.STRENGTH, DifficultyLevel.INTERMEDIATE, "https://example.com/video/squats", "https://example.com/img/squats", 10, true, "Baja la cadera manteniendo la espalda recta.", "Mantén las rodillas alineadas con los pies.", null, null),
                new Exercise(null, "Peso Muerto", "Peso muerto convencional con barra", MuscleGroup.HAMSTRINGS, MuscleGroup.BACK, ExerciseType.STRENGTH, DifficultyLevel.ADVANCED, "https://example.com/video/deadlift", "https://example.com/img/deadlift", 12, true, "Levanta la barra desde el suelo manteniendo la espalda neutra.", "No encorves la espalda baja.", null, null),
                new Exercise(null, "Dominadas", "Dominadas prono en barra fija", MuscleGroup.BACK, MuscleGroup.BICEPS, ExerciseType.STRENGTH, DifficultyLevel.INTERMEDIATE, "https://example.com/video/pullups", "https://example.com/img/pullups", 9, false, "Cuelga de la barra y elévate hasta pasar la barbilla.", "Controla el descenso.", null, null),
                new Exercise(null, "Correr en Cinta", "Cardio en cinta de correr", MuscleGroup.FULL_BODY, null, ExerciseType.CARDIO, DifficultyLevel.BEGINNER, "https://example.com/video/running", "https://example.com/img/running", 15, true, "Corre a velocidad constante.", "Usa calzado adecuado.", null, null)
            );
            exerciseRepository.saveAll(exercises);
            log.info("Successfully seeded {} exercises.", exercises.size());
        } else {
            log.info("Exercises table already has data. Skipping seeding.");
        }
    }
}
