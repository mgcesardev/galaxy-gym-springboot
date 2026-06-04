package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.Payment;
import cmg.example.galaxy_gym_backend.repositories.PaymentRepository;
import cmg.example.galaxy_gym_backend.services.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Payment> findAll(Pageable pageable) {
        try {
            return paymentRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todos los pagos", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Payment findById(Long id) {
        try {
            return paymentRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar pago con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Payment save(Payment model) {
        try {
            return paymentRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar pago: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            paymentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar pago con id " + id + ": " + e.getMessage());
        }
    }


}
