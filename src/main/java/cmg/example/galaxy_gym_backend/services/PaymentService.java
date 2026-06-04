package cmg.example.galaxy_gym_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cmg.example.galaxy_gym_backend.models.Payment;

public interface PaymentService {
    public Page<Payment> findAll(Pageable pageable);

    public Payment findById(Long id);

    public Payment save(Payment model);


    void delete(Long id);

}
