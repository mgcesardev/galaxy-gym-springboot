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
        return routineExerciseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public RoutineExercise findById(Long id) {
        return routineExerciseRepository.findById(id).orElse(null);
    }

    @Override
    public RoutineExercise save(RoutineExercise model) {
        return routineExerciseRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        routineExerciseRepository.deleteById(id);
    }

}
