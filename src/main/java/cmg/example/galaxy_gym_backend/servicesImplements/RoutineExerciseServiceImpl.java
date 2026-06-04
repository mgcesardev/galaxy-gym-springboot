package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.RoutineExercise;
import cmg.example.galaxy_gym_backend.repositories.RoutineExerciseRepository;
import cmg.example.galaxy_gym_backend.services.RoutineExerciseService;

@Service
@Transactional
public class RoutineExerciseServiceImpl implements RoutineExerciseService {

    private final RoutineExerciseRepository routineExerciseRepository;

    public RoutineExerciseServiceImpl(RoutineExerciseRepository routineExerciseRepository) {
        this.routineExerciseRepository = routineExerciseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoutineExercise> findAll(Pageable pageable) {
        try {
            return routineExerciseRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todas las rutinas de ejercicios", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RoutineExercise findById(Long id) {
        try {
            return routineExerciseRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar rutina de ejercicios con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public RoutineExercise save(RoutineExercise model) {
        try {
            return routineExerciseRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar rutina de ejercicios: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            routineExerciseRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar rutina de ejercicios con id " + id + ": " + e.getMessage());
        }
    }


}
