package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;

import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import cmg.example.galaxy_gym_backend.models.RoutineExercise;
import cmg.example.galaxy_gym_backend.models.RoutineExercise.IntensityLevel;
import cmg.example.galaxy_gym_backend.models.WorkoutRoutine;
import cmg.example.galaxy_gym_backend.models.Exercise;
import cmg.example.galaxy_gym_backend.repositories.RoutineExerciseRepository;
import cmg.example.galaxy_gym_backend.repositories.WorkoutRoutineRepository;
import cmg.example.galaxy_gym_backend.repositories.ExerciseRepository;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@Order(11)
public class RoutineExerciseSeeder implements CommandLineRunner {

    private final RoutineExerciseRepository routineExerciseRepository;
    private final WorkoutRoutineRepository workoutRoutineRepository;
    private final ExerciseRepository exerciseRepository;

    public RoutineExerciseSeeder(RoutineExerciseRepository routineExerciseRepository, WorkoutRoutineRepository workoutRoutineRepository, ExerciseRepository exerciseRepository) {
        this.routineExerciseRepository = routineExerciseRepository;
        this.workoutRoutineRepository = workoutRoutineRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (routineExerciseRepository.count() == 0) {
            log.info("Seeding routine exercises...");
            List<WorkoutRoutine> routines = workoutRoutineRepository.findAll();
            List<Exercise> exercises = exerciseRepository.findAll();

            if (routines.isEmpty() || exercises.isEmpty()) {
                log.error("No routines or exercises found to seed routine exercises!");
                return;
            }

            WorkoutRoutine pushRoutine = routines.get(0);
            Exercise benchPress = exercises.stream().filter(e -> e.getName().contains("Banca")).findFirst().orElse(exercises.get(0));

            RoutineExercise re1 = new RoutineExercise(null, pushRoutine, benchPress, 4, 10, 90, 60.0, null, "Enfocarse en la fase excéntrica", 1, IntensityLevel.HIGH);

            routineExerciseRepository.saveAll(Arrays.asList(re1));
            log.info("Successfully seeded routine exercises.");
        } else {
            log.info("Routine exercises table already has data. Skipping seeding.");
        }
    }
}
