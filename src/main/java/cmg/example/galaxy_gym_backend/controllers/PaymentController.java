package cmg.example.galaxy_gym_backend.controllers;

import org.springframework.web.bind.annotation.*;

import cmg.example.galaxy_gym_backend.models.Payment;
import cmg.example.galaxy_gym_backend.services.PaymentService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public Page<Payment> findAll(Pageable pageable) {
        return paymentService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Payment findById(@PathVariable Long id) {
        return paymentService.findById(id);
    }

    @PostMapping
    public Payment save(@RequestBody Payment model) {
        return paymentService.save(model);
    }

    @PatchMapping("/{id}")
    public Payment update(@PathVariable Long id, @RequestBody Payment model) {
        Payment existing = paymentService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return paymentService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        paymentService.delete(id);
    }

}
