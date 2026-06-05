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
        return exerciseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Exercise findById(Long id) {
        return exerciseRepository.findById(id).orElse(null);
    }

    @Override
    public Exercise save(Exercise model) {
        return exerciseRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        exerciseRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Exercise> buscarPorNombre(String nombre, Pageable pageable) {
        return exerciseRepository.buscarPorNombre(nombre, pageable);
    }

}
