package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import cmg.example.galaxy_gym_backend.models.User;
import cmg.example.galaxy_gym_backend.models.Role;
import cmg.example.galaxy_gym_backend.models.Role.RoleType;
import cmg.example.galaxy_gym_backend.repositories.UserRepository;
import cmg.example.galaxy_gym_backend.repositories.RoleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@Order(4)
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserSeeder(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            log.info("Seeding 1000+ users...");
            List<Role> allRoles = roleRepository.findAll();
            Role adminRole = allRoles.stream().filter(r -> r.getName() == RoleType.ROLE_ADMIN).findFirst().orElse(null);
            Role trainerRole = allRoles.stream().filter(r -> r.getName() == RoleType.ROLE_TRAINER).findFirst().orElse(null);
            Role memberRole = allRoles.stream().filter(r -> r.getName() == RoleType.ROLE_MEMBER).findFirst().orElse(null);

            // Generar un hash rápido de BCrypt para evitar tardar minutos hasheando 1000 veces
            String fastHashedPassword = BCrypt.hashpw("password123", BCrypt.gensalt(4));
            List<User> usersToSave = new ArrayList<>();

            // 1. Admin
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@galaxygym.com");
            admin.setPassword(fastHashedPassword);
            admin.setFirstName("Admin");
            admin.setLastName("Galaxy");
            admin.setPhone("1234567890");
            admin.setIsActive(true);
            admin.setIsVerified(true);
            admin.setRoles(Set.of(adminRole));
            usersToSave.add(admin);

            // 2. Trainers (15 entrenadores)
            for (int i = 1; i <= 15; i++) {
                User trainer = new User();
                trainer.setUsername("trainer" + i);
                trainer.setEmail("trainer" + i + "@galaxygym.com");
                trainer.setPassword(fastHashedPassword);
                trainer.setFirstName("Entrenador " + i);
                trainer.setLastName("Gym");
                trainer.setPhone("5551000" + String.format("%03d", i));
                trainer.setIsActive(true);
                trainer.setIsVerified(true);
                trainer.setRoles(Set.of(trainerRole));
                usersToSave.add(trainer);
            }

            // 3. Members (985 miembros)
            for (int i = 1; i <= 985; i++) {
                User member = new User();
                member.setUsername("member" + i);
                member.setEmail("member" + i + "@galaxygym.com");
                member.setPassword(fastHashedPassword);
                member.setFirstName("Miembro " + i);
                member.setLastName("User");
                member.setPhone("5552000" + String.format("%03d", i));
                member.setIsActive(true);
                member.setIsVerified(true);
                member.setRoles(Set.of(memberRole));
                usersToSave.add(member);
            }

            userRepository.saveAll(usersToSave);
            log.info("Successfully seeded {} users.", usersToSave.size());
        } else {
            log.info("Users table already has data. Skipping seeding.");
        }
    }
}
