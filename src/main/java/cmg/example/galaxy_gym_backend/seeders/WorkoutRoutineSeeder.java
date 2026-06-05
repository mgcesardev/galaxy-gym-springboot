package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;

import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import cmg.example.galaxy_gym_backend.models.WorkoutRoutine;
import cmg.example.galaxy_gym_backend.models.WorkoutRoutine.RoutineType;
import cmg.example.galaxy_gym_backend.models.Exercise.DifficultyLevel;
import cmg.example.galaxy_gym_backend.models.Trainer;
import cmg.example.galaxy_gym_backend.repositories.WorkoutRoutineRepository;
import cmg.example.galaxy_gym_backend.repositories.TrainerRepository;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@Order(10)
public class WorkoutRoutineSeeder implements CommandLineRunner {

    private final WorkoutRoutineRepository workoutRoutineRepository;
    private final TrainerRepository trainerRepository;

    public WorkoutRoutineSeeder(WorkoutRoutineRepository workoutRoutineRepository, TrainerRepository trainerRepository) {
        this.workoutRoutineRepository = workoutRoutineRepository;
        this.trainerRepository = trainerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (workoutRoutineRepository.count() == 0) {
            log.info("Seeding workout routines...");
            List<Trainer> trainers = trainerRepository.findAll();
            if (trainers.isEmpty()) {
                log.error("No trainers found to seed workout routines!");
                return;
            }

            Trainer trainer = trainers.get(0);

            List<WorkoutRoutine> routines = Arrays.asList(
                new WorkoutRoutine(null, "Rutina de Fuerza Push", "Enfocada en empuje: Pecho, Hombro y Tríceps", trainer.getUser(), trainer, RoutineType.STRENGTH_TRAINING, DifficultyLevel.BEGINNER, 60, 400, 90, null, "Calentar bien los manguitos rotadores", false, true, 5, 4.5, null, null),
                new WorkoutRoutine(null, "Rutina Pull - Espalda y Biceps", "Enfocada en tracción y flexión de brazos", trainer.getUser(), trainer, RoutineType.STRENGTH_TRAINING, DifficultyLevel.INTERMEDIATE, 50, 350, 75, null, "Enfocarse en la contracción dorsal", false, true, 8, 4.7, null, null),
                new WorkoutRoutine(null, "Acondicionamiento HIIT Full Body", "Entrenamiento de intervalos de alta intensidad", trainer.getUser(), trainer, RoutineType.HIIT, DifficultyLevel.ADVANCED, 30, 500, 45, null, "Mantener el ritmo cardíaco alto", false, true, 12, 4.8, null, null)
            );
            // Wait, we have a dash typo above again in `- new WorkoutRoutine(...)`. Let's remove it in the replacement text.
            workoutRoutineRepository.saveAll(routines);
            log.info("Successfully seeded {} workout routines.", routines.size());
        } else {
            log.info("Workout routines table already has data. Skipping seeding.");
        }
    }
}
