package cmg.example.galaxy_gym_backend.controllers;

import org.springframework.web.bind.annotation.*;

import cmg.example.galaxy_gym_backend.models.RoutineExercise;
import cmg.example.galaxy_gym_backend.services.RoutineExerciseService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/routine-exercises")
public class RoutineExerciseController {

    private final RoutineExerciseService routineExerciseService;

    public RoutineExerciseController(RoutineExerciseService routineExerciseService) {
        this.routineExerciseService = routineExerciseService;
    }

    @GetMapping
    public Page<RoutineExercise> findAll(Pageable pageable) {
        return routineExerciseService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public RoutineExercise findById(@PathVariable Long id) {
        return routineExerciseService.findById(id);
    }

    @PostMapping
    public RoutineExercise save(@RequestBody RoutineExercise model) {
        return routineExerciseService.save(model);
    }

    @PatchMapping("/{id}")
    public RoutineExercise update(@PathVariable Long id, @RequestBody RoutineExercise model) {
        RoutineExercise existing = routineExerciseService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return routineExerciseService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        routineExerciseService.delete(id);
    }

}
