package cmg.example.galaxy_gym_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cmg.example.galaxy_gym_backend.models.WorkoutRoutine;

public interface WorkoutRoutineService {
    public Page<WorkoutRoutine> findAll(Pageable pageable);

    public WorkoutRoutine findById(Long id);

    public WorkoutRoutine save(WorkoutRoutine model);

    Page<WorkoutRoutine> buscarPorNombre(String nombre, Pageable pageable);

    void delete(Long id);
}
