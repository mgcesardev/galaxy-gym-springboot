package cmg.example.galaxy_gym_backend.controllers;

import cmg.example.galaxy_gym_backend.models.Equipment;
import cmg.example.galaxy_gym_backend.services.EquipmentService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equipments")
public class EquipmentController {

    private final EquipmentService equipmentService;
    

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public Page<Equipment> findAll(Pageable pageable) {
        return equipmentService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Equipment findById(@PathVariable Long id) {
        return equipmentService.findById(id);
    }

    @PostMapping
    public Equipment save(@RequestBody Equipment model) {
        return equipmentService.save(model);
    }

    @PatchMapping("/{id}")
    public Equipment update(@PathVariable Long id, @RequestBody Equipment model) {
        Equipment existing = equipmentService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return equipmentService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        equipmentService.delete(id);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<Equipment>> buscarPorNombre(
            @RequestParam String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Equipment> resultados = equipmentService.buscarPorNombre(nombre, pageable);
            return new ResponseEntity<>(resultados, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
