package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.WorkoutRoutine;
import cmg.example.galaxy_gym_backend.repositories.WorkoutRoutineRepository;
import cmg.example.galaxy_gym_backend.services.WorkoutRoutineService;

@Service
@Transactional
public class WorkoutRoutineServiceImpl implements WorkoutRoutineService {

    private final WorkoutRoutineRepository workoutRoutineRepository;

    public WorkoutRoutineServiceImpl(WorkoutRoutineRepository workoutRoutineRepository) {
        this.workoutRoutineRepository = workoutRoutineRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkoutRoutine> findAll(Pageable pageable) {
        try {
            return workoutRoutineRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todas las rutinas de ejercicios", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutRoutine findById(Long id) {
        try {
            return workoutRoutineRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar rutina de ejercicios con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public WorkoutRoutine save(WorkoutRoutine model) {
        try {
            return workoutRoutineRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar rutina de ejercicios: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            workoutRoutineRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar rutina de ejercicios con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Page<WorkoutRoutine> buscarPorNombre(String nombre, Pageable pageable) {
        try {
            return workoutRoutineRepository.buscarPorNombre(nombre, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar rutina de ejercicios por nombre: " + e.getMessage());
        }
    }

}
