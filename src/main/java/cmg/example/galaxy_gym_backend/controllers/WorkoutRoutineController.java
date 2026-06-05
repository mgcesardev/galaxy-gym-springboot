package cmg.example.galaxy_gym_backend.controllers;

import org.springframework.web.bind.annotation.*;

import cmg.example.galaxy_gym_backend.models.WorkoutRoutine;
import cmg.example.galaxy_gym_backend.services.WorkoutRoutineService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/workout-routines")
public class WorkoutRoutineController {

    private final WorkoutRoutineService workoutRoutineService;

    public WorkoutRoutineController(WorkoutRoutineService workoutRoutineService) {
        this.workoutRoutineService = workoutRoutineService;
    }

    @GetMapping
    public Page<WorkoutRoutine> findAll(Pageable pageable) {
        return workoutRoutineService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public WorkoutRoutine findById(@PathVariable Long id) {
        return workoutRoutineService.findById(id);
    }

    @PostMapping
    public WorkoutRoutine save(@RequestBody WorkoutRoutine model) {
        return workoutRoutineService.save(model);
    }

    @PatchMapping("/{id}")
    public WorkoutRoutine update(@PathVariable Long id, @RequestBody WorkoutRoutine model) {
        WorkoutRoutine existing = workoutRoutineService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return workoutRoutineService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workoutRoutineService.delete(id);
    }

}
