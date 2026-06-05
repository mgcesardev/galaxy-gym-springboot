package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import cmg.example.galaxy_gym_backend.models.Attendance;
import cmg.example.galaxy_gym_backend.models.Attendance.AttendanceType;
import cmg.example.galaxy_gym_backend.models.Membership;
import cmg.example.galaxy_gym_backend.repositories.AttendanceRepository;
import cmg.example.galaxy_gym_backend.repositories.MembershipRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Order(9)
public class AttendanceSeeder implements CommandLineRunner {

    private final AttendanceRepository attendanceRepository;
    private final MembershipRepository membershipRepository;

    public AttendanceSeeder(AttendanceRepository attendanceRepository, MembershipRepository membershipRepository) {
        this.attendanceRepository = attendanceRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (attendanceRepository.count() == 0) {
            log.info("Seeding 1000+ attendances...");
            List<Membership> memberships = membershipRepository.findAll();
            if (memberships.isEmpty()) {
                log.error("No memberships found to seed attendances!");
                return;
            }

            List<Attendance> attendancesToSave = new ArrayList<>();

            // 1 asistencia para cada miembro (~985)
            for (Membership m : memberships) {
                Attendance a = new Attendance();
                a.setUser(m.getUser());
                a.setMembership(m);
                a.setCheckIn(LocalDateTime.now().minusDays(1).withHour(8).withMinute(0));
                a.setCheckOut(LocalDateTime.now().minusDays(1).withHour(9).withMinute(30));
                a.setType(AttendanceType.GYM_ACCESS);
                a.setNotes("Ingreso regular");
                a.setIpAddress("192.168.1.100");
                a.setDeviceInfo("Turnstile Gate 1");
                attendancesToSave.add(a);
            }

            // Asistencias adicionales para llegar a más de 1000 (por ejemplo, asistencias de hace 2 días para los primeros 150 usuarios)
            for (int i = 0; i < 150 && i < memberships.size(); i++) {
                Membership m = memberships.get(i);
                Attendance a = new Attendance();
                a.setUser(m.getUser());
                a.setMembership(m);
                a.setCheckIn(LocalDateTime.now().minusDays(2).withHour(18).withMinute(15));
                a.setCheckOut(LocalDateTime.now().minusDays(2).withHour(19).withMinute(45));
                a.setType(AttendanceType.GYM_ACCESS);
                a.setNotes("Ingreso regular tarde");
                a.setIpAddress("192.168.1.100");
                a.setDeviceInfo("Turnstile Gate 1");
                attendancesToSave.add(a);
            }

            attendanceRepository.saveAll(attendancesToSave);
            log.info("Successfully seeded {} attendances.", attendancesToSave.size());
        } else {
            log.info("Attendances table already has data. Skipping seeding.");
        }
    }
}
