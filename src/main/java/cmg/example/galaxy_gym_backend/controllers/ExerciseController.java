package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.models.Exercise;
import cmg.example.galaxy_gym_backend.services.ExerciseService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public Page<Exercise> findAll(Pageable pageable) {
        return exerciseService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Exercise findById(@PathVariable Long id) {
        return exerciseService.findById(id);
    }

    @PostMapping
    public Exercise save(@RequestBody Exercise model) {
        return exerciseService.save(model);
    }

    @PatchMapping("/{id}")
    public Exercise update(@PathVariable Long id, @RequestBody Exercise model) {
        Exercise existing = exerciseService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return exerciseService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        exerciseService.delete(id);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<Exercise>> buscarPorNombre(
            @RequestParam String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Exercise> resultados = exerciseService.buscarPorNombre(nombre, pageable);
            return new ResponseEntity<>(resultados, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
