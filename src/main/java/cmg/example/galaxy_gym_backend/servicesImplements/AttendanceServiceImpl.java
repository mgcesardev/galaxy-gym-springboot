package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.Attendance;
import cmg.example.galaxy_gym_backend.repositories.AttendanceRepository;
import cmg.example.galaxy_gym_backend.services.AttendanceService;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Attendance> findAll(Pageable pageable) {
        return attendanceRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Attendance findById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    @Override
    public Attendance save(Attendance model) {
        return attendanceRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        attendanceRepository.deleteById(id);
    }

}
