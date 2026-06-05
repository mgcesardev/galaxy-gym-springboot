package cmg.example.galaxy_gym_backend.controllers;

import org.springframework.web.bind.annotation.*;

import cmg.example.galaxy_gym_backend.models.Role;
import cmg.example.galaxy_gym_backend.services.RoleService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public Page<Role> findAll(Pageable pageable) {
        return roleService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Role findById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    @PostMapping
    public Role save(@RequestBody Role model) {
        return roleService.save(model);
    }

    @PatchMapping("/{id}")
    public Role update(@PathVariable Long id, @RequestBody Role model) {
        Role existing = roleService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return roleService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }

}
