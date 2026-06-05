package cmg.example.galaxy_gym_backend.controllers;

import org.springframework.web.bind.annotation.*;

import cmg.example.galaxy_gym_backend.models.Notification;
import cmg.example.galaxy_gym_backend.services.NotificationService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public Page<Notification> findAll(Pageable pageable) {
        return notificationService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Notification findById(@PathVariable Long id) {
        return notificationService.findById(id);
    }

    @PostMapping
    public Notification save(@RequestBody Notification model) {
        return notificationService.save(model);
    }

    @PatchMapping("/{id}")
    public Notification update(@PathVariable Long id, @RequestBody Notification model) {
        Notification existing = notificationService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return notificationService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationService.delete(id);
    }

}
