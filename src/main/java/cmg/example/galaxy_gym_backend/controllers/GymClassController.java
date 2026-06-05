package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.models.GymClass;
import cmg.example.galaxy_gym_backend.services.GymClassService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gym-classes")
public class GymClassController {

    private final GymClassService gymClassService;

    public GymClassController(GymClassService gymClassService) {
        this.gymClassService = gymClassService;
    }

    @GetMapping
    public Page<GymClass> findAll(Pageable pageable) {
        return gymClassService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public GymClass findById(@PathVariable Long id) {
        return gymClassService.findById(id);
    }

    @PostMapping
    public GymClass save(@RequestBody GymClass model) {
        return gymClassService.save(model);
    }

    @PatchMapping("/{id}")
    public GymClass update(@PathVariable Long id, @RequestBody GymClass model) {
        GymClass existing = gymClassService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return gymClassService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        gymClassService.delete(id);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<GymClass>> buscarPorNombre(
            @RequestParam String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<GymClass> resultados = gymClassService.buscarPorNombre(nombre, pageable);
            return new ResponseEntity<>(resultados, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
