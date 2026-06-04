package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.Exercise;
import cmg.example.galaxy_gym_backend.repositories.ExerciseRepository;
import cmg.example.galaxy_gym_backend.services.ExerciseService;

@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Exercise> findAll(Pageable pageable) {
        try {
            return exerciseRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todos los ejercicios", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Exercise findById(Long id) {
        try {
            return exerciseRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar ejercicio con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Exercise save(Exercise model) {
        try {
            return exerciseRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar ejercicio: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            exerciseRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar ejercicio con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Exercise> buscarPorNombre(String nombre, Pageable pageable) {
        try {
            return exerciseRepository.buscarPorNombre(nombre, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar ejercicio por nombre: " + e.getMessage());
        }
    }

}
