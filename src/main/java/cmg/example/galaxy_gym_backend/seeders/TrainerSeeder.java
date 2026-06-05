package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import cmg.example.galaxy_gym_backend.models.Trainer;
import cmg.example.galaxy_gym_backend.models.Trainer.TrainerStatus;
import cmg.example.galaxy_gym_backend.models.User;
import cmg.example.galaxy_gym_backend.repositories.TrainerRepository;
import cmg.example.galaxy_gym_backend.repositories.UserRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@Order(5)
public class TrainerSeeder implements CommandLineRunner {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;

    public TrainerSeeder(TrainerRepository trainerRepository, UserRepository userRepository) {
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (trainerRepository.count() == 0) {
            log.info("Seeding trainers...");
            // Get all user trainers
            List<User> trainerUsers = userRepository.findAll().stream()
                .filter(u -> u.getUsername().startsWith("trainer"))
                .collect(Collectors.toList());

            List<Trainer> trainersToSave = new ArrayList<>();
            for (User u : trainerUsers) {
                Trainer t = new Trainer();
                t.setUser(u);
                t.setSpecialization("Fuerza e Hipertrofia");
                t.setCertifications("Certificado Personal Trainer NCSF");
                t.setYearsOfExperience(5);
                t.setBiography("Entrenador entusiasta especializado en acondicionamiento físico general.");
                t.setHourlyRate(new BigDecimal("25.0"));
                t.setStatus(TrainerStatus.ACTIVE);
                t.setSchedule("Lunes a Viernes de 6:00 AM a 2:00 PM");
                t.setMaxClients(20);
                t.setRating(4.8);
                trainersToSave.add(t);
            }

            trainerRepository.saveAll(trainersToSave);
            log.info("Successfully seeded {} trainers.", trainersToSave.size());
        } else {
            log.info("Trainers table already has data. Skipping seeding.");
        }
    }
}
