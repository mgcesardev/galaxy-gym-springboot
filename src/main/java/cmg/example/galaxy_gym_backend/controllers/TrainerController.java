package cmg.example.galaxy_gym_backend.controllers;

import org.springframework.web.bind.annotation.*;

import cmg.example.galaxy_gym_backend.models.Trainer;
import cmg.example.galaxy_gym_backend.services.TrainerService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping
    public Page<Trainer> findAll(Pageable pageable) {
        return trainerService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Trainer findById(@PathVariable Long id) {
        return trainerService.findById(id);
    }

    @PostMapping
    public Trainer save(@RequestBody Trainer model) {
        return trainerService.save(model);
    }

    @PatchMapping("/{id}")
    public Trainer update(@PathVariable Long id, @RequestBody Trainer model) {
        Trainer existing = trainerService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return trainerService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        trainerService.delete(id);
    }

}
