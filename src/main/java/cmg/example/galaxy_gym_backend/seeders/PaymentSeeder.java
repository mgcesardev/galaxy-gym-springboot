package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import cmg.example.galaxy_gym_backend.models.Payment;
import cmg.example.galaxy_gym_backend.models.Payment.PaymentStatus;
import cmg.example.galaxy_gym_backend.models.Membership;
import cmg.example.galaxy_gym_backend.models.Membership.PaymentMethod;
import cmg.example.galaxy_gym_backend.repositories.PaymentRepository;
import cmg.example.galaxy_gym_backend.repositories.MembershipRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Order(8)
public class PaymentSeeder implements CommandLineRunner {

    private final PaymentRepository paymentRepository;
    private final MembershipRepository membershipRepository;

    public PaymentSeeder(PaymentRepository paymentRepository, MembershipRepository membershipRepository) {
        this.paymentRepository = paymentRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (paymentRepository.count() == 0) {
            log.info("Seeding 1000+ payments...");
            List<Membership> memberships = membershipRepository.findAll();
            if (memberships.isEmpty()) {
                log.error("No memberships found to seed payments!");
                return;
            }

            List<Payment> paymentsToSave = new ArrayList<>();

            // Generamos al menos 1 pago por cada membresía (985)
            for (Membership m : memberships) {
                Payment p = new Payment();
                p.setMembership(m);
                p.setUser(m.getUser());
                p.setAmount(m.getAmountPaid());
                p.setPaymentDate(LocalDateTime.now().minusDays(10));
                p.setStatus(PaymentStatus.COMPLETED);
                p.setMethod(m.getPaymentMethod() != null ? m.getPaymentMethod() : PaymentMethod.CREDIT_CARD);
                p.setTransactionId("TXN-" + System.currentTimeMillis() + "-" + m.getId());
                p.setDescription("Pago mensualidad activa");
                paymentsToSave.add(p);
            }

            // Generamos pagos adicionales para llegar a más de 1000 (por ejemplo, pagos del mes anterior para los primeros 100 usuarios)
            for (int i = 0; i < 100 && i < memberships.size(); i++) {
                Membership m = memberships.get(i);
                Payment p = new Payment();
                p.setMembership(m);
                p.setUser(m.getUser());
                p.setAmount(m.getAmountPaid());
                p.setPaymentDate(LocalDateTime.now().minusDays(40));
                p.setStatus(PaymentStatus.COMPLETED);
                p.setMethod(m.getPaymentMethod() != null ? m.getPaymentMethod() : PaymentMethod.CREDIT_CARD);
                p.setTransactionId("TXN-OLD-" + System.currentTimeMillis() + "-" + m.getId());
                p.setDescription("Pago mensualidad mes anterior");
                paymentsToSave.add(p);
            }

            paymentRepository.saveAll(paymentsToSave);
            log.info("Successfully seeded {} payments.", paymentsToSave.size());
        } else {
            log.info("Payments table already has data. Skipping seeding.");
        }
    }
}
