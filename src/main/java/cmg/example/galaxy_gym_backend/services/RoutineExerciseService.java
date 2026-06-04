package cmg.example.galaxy_gym_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cmg.example.galaxy_gym_backend.models.RoutineExercise;

public interface RoutineExerciseService {
    public Page<RoutineExercise> findAll(Pageable pageable);

    public RoutineExercise findById(Long id);

    public RoutineExercise save(RoutineExercise model);

    Page<RoutineExercise> buscarPorNombre(String nombre, Pageable pageable);

}
