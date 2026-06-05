package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;

import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import cmg.example.galaxy_gym_backend.models.Reservation;
import cmg.example.galaxy_gym_backend.models.Reservation.ReservationStatus;
import cmg.example.galaxy_gym_backend.models.Reservation.AttendanceStatus;
import cmg.example.galaxy_gym_backend.models.GymClass;
import cmg.example.galaxy_gym_backend.models.User;
import cmg.example.galaxy_gym_backend.repositories.ReservationRepository;
import cmg.example.galaxy_gym_backend.repositories.GymClassRepository;
import cmg.example.galaxy_gym_backend.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@Order(13)
public class ReservationSeeder implements CommandLineRunner {

    private final ReservationRepository reservationRepository;
    private final GymClassRepository gymClassRepository;
    private final UserRepository userRepository;

    public ReservationSeeder(ReservationRepository reservationRepository, GymClassRepository gymClassRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.gymClassRepository = gymClassRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (reservationRepository.count() == 0) {
            log.info("Seeding 1000+ reservations...");
            List<User> members = userRepository.findAll().stream()
                .filter(u -> u.getUsername().startsWith("member"))
                .collect(Collectors.toList());

            List<GymClass> classes = gymClassRepository.findAll();
            if (members.isEmpty() || classes.isEmpty()) {
                log.error("No members or classes found to seed reservations!");
                return;
            }

            List<Reservation> reservationsToSave = new ArrayList<>();

            // Registrar cada miembro a la clase 1 (~985 reservas)
            GymClass class1 = classes.get(0);
            for (User u : members) {
                Reservation r = new Reservation();
                r.setUser(u);
                r.setClassEntity(class1);
                r.setReservationDate(LocalDateTime.now().minusDays(1));
                r.setStatus(ReservationStatus.CONFIRMED);
                r.setAttendanceStatus(AttendanceStatus.ATTENDED);
                r.setNotes("Reservación regular");
                reservationsToSave.add(r);
            }

            // Registrar los primeros 100 miembros a la clase 2 (100 reservas)
            if (classes.size() > 1) {
                GymClass class2 = classes.get(1);
                for (int i = 0; i < 100 && i < members.size(); i++) {
                    User u = members.get(i);
                    Reservation r = new Reservation();
                    r.setUser(u);
                    r.setClassEntity(class2);
                    r.setReservationDate(LocalDateTime.now().plusHours(2));
                    r.setStatus(ReservationStatus.CONFIRMED);
                    r.setAttendanceStatus(AttendanceStatus.REGISTERED);
                    r.setNotes("Reservación para clase nocturna");
                    reservationsToSave.add(r);
                }
            }

            reservationRepository.saveAll(reservationsToSave);
            log.info("Successfully seeded {} reservations.", reservationsToSave.size());
        } else {
            log.info("Reservations table already has data. Skipping seeding.");
        }
    }
}
