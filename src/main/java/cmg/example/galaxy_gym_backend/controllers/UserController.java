package cmg.example.galaxy_gym_backend.controllers;

import org.springframework.web.bind.annotation.*;

import cmg.example.galaxy_gym_backend.models.User;
import cmg.example.galaxy_gym_backend.services.UserService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<User> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public User save(@RequestBody User model) {
        return userService.save(model);
    }

    @PatchMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User model) {
        User existing = userService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return userService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
