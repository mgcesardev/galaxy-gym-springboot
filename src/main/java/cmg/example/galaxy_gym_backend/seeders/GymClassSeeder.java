package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import cmg.example.galaxy_gym_backend.models.GymClass;
import cmg.example.galaxy_gym_backend.models.GymClass.ClassType;
import cmg.example.galaxy_gym_backend.models.GymClass.ClassStatus;
import cmg.example.galaxy_gym_backend.models.Exercise.DifficultyLevel;
import cmg.example.galaxy_gym_backend.models.Trainer;
import cmg.example.galaxy_gym_backend.repositories.GymClassRepository;
import cmg.example.galaxy_gym_backend.repositories.TrainerRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@Order(12)
public class GymClassSeeder implements CommandLineRunner {

    private final GymClassRepository gymClassRepository;
    private final TrainerRepository trainerRepository;

    public GymClassSeeder(GymClassRepository gymClassRepository, TrainerRepository trainerRepository) {
        this.gymClassRepository = gymClassRepository;
        this.trainerRepository = trainerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (gymClassRepository.count() == 0) {
            log.info("Seeding gym classes...");
            List<Trainer> trainers = trainerRepository.findAll();
            if (trainers.isEmpty()) {
                log.error("No trainers found to seed gym classes!");
                return;
            }

            Trainer trainer = trainers.get(0);

            List<GymClass> classes = Arrays.asList(
                new GymClass(null, "Clase de Yoga Flow", "Clase de vinyasa yoga para mejorar flexibilidad y balance", trainer, ClassType.YOGA, LocalDateTime.now().plusDays(1).withHour(9).withMinute(0), LocalDateTime.now().plusDays(1).withHour(10).withMinute(0), 20, 0, "Salón A", DifficultyLevel.BEGINNER, 10.0, true, "WEEKLY", ClassStatus.SCHEDULED, null, null, null),
                new GymClass(null, "Spinning Extremo", "Indoor cycling de alta intensidad cardiovascular", trainer, ClassType.SPINNING, LocalDateTime.now().plusDays(1).withHour(18).withMinute(0), LocalDateTime.now().plusDays(1).withHour(19).withMinute(0), 25, 0, "Salón de Ciclo", DifficultyLevel.INTERMEDIATE, 15.0, true, "WEEKLY", ClassStatus.SCHEDULED, null, null, null),
                new GymClass(null, "Crossfit WOD", "Acondicionamiento físico de alta intensidad", trainer, ClassType.CROSSFIT, LocalDateTime.now().plusDays(2).withHour(7).withMinute(0), LocalDateTime.now().plusDays(2).withHour(8).withMinute(0), 15, 0, "Box de Crossfit", DifficultyLevel.ADVANCED, 20.0, false, null, ClassStatus.SCHEDULED, null, null, null)
            );

            gymClassRepository.saveAll(classes);
            log.info("Successfully seeded {} gym classes.", classes.size());
        } else {
            log.info("Gym classes table already has data. Skipping seeding.");
        }
    }
}
