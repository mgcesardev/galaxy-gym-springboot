package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import cmg.example.galaxy_gym_backend.models.Membership;
import cmg.example.galaxy_gym_backend.models.Membership.MembershipStatus;
import cmg.example.galaxy_gym_backend.models.Membership.PaymentMethod;
import cmg.example.galaxy_gym_backend.models.MembershipPlan;
import cmg.example.galaxy_gym_backend.models.User;
import cmg.example.galaxy_gym_backend.repositories.MembershipPlanRepository;
import cmg.example.galaxy_gym_backend.repositories.MembershipRepository;
import cmg.example.galaxy_gym_backend.repositories.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@Order(7)
public class MembershipSeeder implements CommandLineRunner {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final MembershipPlanRepository membershipPlanRepository;

    public MembershipSeeder(MembershipRepository membershipRepository, UserRepository userRepository, MembershipPlanRepository membershipPlanRepository) {
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.membershipPlanRepository = membershipPlanRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (membershipRepository.count() == 0) {
            log.info("Seeding memberships for all members...");
            List<User> members = userRepository.findAll().stream()
                .filter(u -> u.getUsername().startsWith("member"))
                .collect(Collectors.toList());

            List<MembershipPlan> plans = membershipPlanRepository.findAll();
            if (plans.isEmpty()) {
                log.error("No membership plans found to seed memberships!");
                return;
            }

            List<Membership> membershipsToSave = new ArrayList<>();
            int planCount = plans.size();

            for (int i = 0; i < members.size(); i++) {
                User member = members.get(i);
                MembershipPlan plan = plans.get(i % planCount);

                Membership m = new Membership();
                m.setUser(member);
                m.setPlan(plan);
                m.setStartDate(LocalDate.now().minusDays(15));
                m.setEndDate(LocalDate.now().plusDays(plan.getDurationDays() - 15));
                m.setStatus(MembershipStatus.ACTIVE);
                m.setAmountPaid(plan.getPrice());
                m.setPaymentMethod(PaymentMethod.CREDIT_CARD);
                membershipsToSave.add(m);
            }

            membershipRepository.saveAll(membershipsToSave);
            log.info("Successfully seeded {} memberships.", membershipsToSave.size());
        } else {
            log.info("Memberships table already has data. Skipping seeding.");
        }
    }
}
