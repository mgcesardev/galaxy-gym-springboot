package cmg.example.galaxy_gym_backend.services;

import cmg.example.galaxy_gym_backend.models.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttendanceService {

    public Page<Attendance> findAll(Pageable pageable);

    public Attendance findById(Long id);

    public Attendance save(Attendance model);

    void delete(Long id);


}
