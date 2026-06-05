package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;

import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import cmg.example.galaxy_gym_backend.models.Notification;
import cmg.example.galaxy_gym_backend.models.Notification.NotificationType;
import cmg.example.galaxy_gym_backend.models.Notification.NotificationPriority;
import cmg.example.galaxy_gym_backend.models.User;
import cmg.example.galaxy_gym_backend.repositories.NotificationRepository;
import cmg.example.galaxy_gym_backend.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@Order(14)
public class NotificationSeeder implements CommandLineRunner {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationSeeder(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (notificationRepository.count() == 0) {
            log.info("Seeding notifications...");
            List<User> members = userRepository.findAll().stream()
                .filter(u -> u.getUsername().startsWith("member"))
                .limit(50)
                .collect(Collectors.toList());

            if (members.isEmpty()) {
                log.error("No members found to seed notifications!");
                return;
            }

            List<Notification> notificationsToSave = new ArrayList<>();
            for (User u : members) {
                Notification n = new Notification();
                n.setUser(u);
                n.setTitle("¡Bienvenido a Galaxy Gym!");
                n.setMessage("Estamos encantados de tenerte con nosotros. Explora nuestras clases y rutinas.");
                n.setType(NotificationType.WELCOME);
                n.setIsRead(false);
                n.setPriority(NotificationPriority.LOW);
                n.setExpiresAt(LocalDateTime.now().plusMonths(1));
                notificationsToSave.add(n);
            }

            notificationRepository.saveAll(notificationsToSave);
            log.info("Successfully seeded {} notifications.", notificationsToSave.size());
        } else {
            log.info("Notifications table already has data. Skipping seeding.");
        }
    }
}
