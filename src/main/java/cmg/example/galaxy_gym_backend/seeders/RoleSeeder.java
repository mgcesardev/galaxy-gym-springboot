package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;

import cmg.example.galaxy_gym_backend.models.Role;
import cmg.example.galaxy_gym_backend.models.Role.RoleType;
import cmg.example.galaxy_gym_backend.repositories.RoleRepository;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Order(1)
public class RoleSeeder implements CommandLineRunner{
 
    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedRoles();
    }

    private void seedRoles() {
        if (roleRepository.count() == 0) {
            log.info("Seeding initial roles...");

            List<Role> roles = Arrays.asList(
                    new Role(null, RoleType.ROLE_ADMIN, "Administrador del sistema"),
                    new Role(null, RoleType.ROLE_TRAINER, "Entrenador del gimnasio"),
                    new Role(null, RoleType.ROLE_MEMBER, "Miembro del gimnasio"),
                    new Role(null, RoleType.ROLE_RECEPTIONIST, "Recepcionista del gimnasio"),
                    new Role(null, RoleType.ROLE_NUTRITIONIST, "Nutricionista del gimnasio"),
                    new Role(null, RoleType.ROLE_PHYSICAL_THERAPIST, "Fisioterapeuta del gimnasio"),
                    new Role(null, RoleType.ROLE_MANAGER, "Gerente del gimnasio")
                );

            roleRepository.saveAll(roles);
            log.info("Successfully seeded {} roles.", roles.size());
        } else {
            log.info("Roles table already has data. Skipping seeding.");
        }
    }
}
