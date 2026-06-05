package cmg.example.galaxy_gym_backend.controllers;

import org.springframework.web.bind.annotation.*;

import cmg.example.galaxy_gym_backend.models.Reservation;
import cmg.example.galaxy_gym_backend.services.ReservationService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public Page<Reservation> findAll(Pageable pageable) {
        return reservationService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Reservation findById(@PathVariable Long id) {
        return reservationService.findById(id);
    }

    @PostMapping
    public Reservation save(@RequestBody Reservation model) {
        return reservationService.save(model);
    }

    @PatchMapping("/{id}")
    public Reservation update(@PathVariable Long id, @RequestBody Reservation model) {
        Reservation existing = reservationService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return reservationService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationService.delete(id);
    }

}
