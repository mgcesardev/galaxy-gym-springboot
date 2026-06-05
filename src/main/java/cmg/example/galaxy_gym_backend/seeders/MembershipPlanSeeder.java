package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import cmg.example.galaxy_gym_backend.models.MembershipPlan;
import cmg.example.galaxy_gym_backend.models.MembershipPlan.PlanStatus;
import cmg.example.galaxy_gym_backend.repositories.MembershipPlanRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@Order(6)
public class MembershipPlanSeeder implements CommandLineRunner {

    private final MembershipPlanRepository membershipPlanRepository;

    public MembershipPlanSeeder(MembershipPlanRepository membershipPlanRepository) {
        this.membershipPlanRepository = membershipPlanRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (membershipPlanRepository.count() == 0) {
            log.info("Seeding membership plans...");
            List<MembershipPlan> plans = Arrays.asList(
                new MembershipPlan(null, "Plan Básico", "Acceso limitado a zona de pesas y cardio", 30, new BigDecimal("30.0"), BigDecimal.ZERO, 0, false, false, false, PlanStatus.ACTIVE, null, null),
                new MembershipPlan(null, "Plan Plata", "Acceso libre a pesas, cardio y 2 clases grupales semanales", 30, new BigDecimal("50.0"), BigDecimal.ZERO, 2, false, false, false, PlanStatus.ACTIVE, null, null),
                new MembershipPlan(null, "Plan Oro", "Acceso ilimitado a todas las instalaciones, clases grupales y entrenador", 30, new BigDecimal("80.0"), BigDecimal.ZERO, 99, true, true, true, PlanStatus.ACTIVE, null, null),
                new MembershipPlan(null, "Plan VIP Anual", "Plan premium anual con todos los beneficios incluidos", 365, new BigDecimal("600.0"), new BigDecimal("100.0"), 99, true, true, true, PlanStatus.ACTIVE, null, null)
            );
            // Wait, new MembershipPlan above has "- " in front of it? Ah, let me write it cleanly.
            // Let's remove the dash typo
            membershipPlanRepository.saveAll(plans);
            log.info("Successfully seeded {} membership plans.", plans.size());
        } else {
            log.info("Membership plans table already has data. Skipping seeding.");
        }
    }
}
