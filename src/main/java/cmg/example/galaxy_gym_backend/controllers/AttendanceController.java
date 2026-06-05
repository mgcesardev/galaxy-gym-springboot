package cmg.example.galaxy_gym_backend.controllers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cmg.example.galaxy_gym_backend.models.Attendance;
import cmg.example.galaxy_gym_backend.services.AttendanceService;
import cmg.example.galaxy_gym_backend.utils.PatchHelper;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public Page<Attendance> findAll(Pageable pageable) {
        return attendanceService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Attendance findById(@PathVariable Long id) {
        return attendanceService.findById(id);
    }

    @PostMapping
    public Attendance save(@RequestBody Attendance model) {
        return attendanceService.save(model);
    }

    @PatchMapping("/{id}")
    public Attendance update(@PathVariable Long id, @RequestBody Attendance model) {
        Attendance existing = attendanceService.findById(id);
        if (existing != null) {
            PatchHelper.copyNonNullProperties(model, existing);
            return attendanceService.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        attendanceService.delete(id);
    }

}

