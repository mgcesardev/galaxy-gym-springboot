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
        return workoutRoutineRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutRoutine findById(Long id) {
        return workoutRoutineRepository.findById(id).orElse(null);
    }

    @Override
    public WorkoutRoutine save(WorkoutRoutine model) {
        return workoutRoutineRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        workoutRoutineRepository.deleteById(id);
    }

    @Override
    public Page<WorkoutRoutine> buscarPorNombre(String nombre, Pageable pageable) {
        return workoutRoutineRepository.buscarPorNombre(nombre, pageable);
    }

}
