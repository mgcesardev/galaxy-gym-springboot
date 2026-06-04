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
        try {
            return attendanceRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todas las asistencias", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Attendance findById(Long id) {
        try {
            return attendanceRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error al encontrar asistencia con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Attendance save(Attendance model) {
        try {
            return attendanceRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar asistencia: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            attendanceRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar asistencia con id " + id + ": " + e.getMessage());
        }
    }


}
