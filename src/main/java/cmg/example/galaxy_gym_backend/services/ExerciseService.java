package cmg.example.galaxy_gym_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cmg.example.galaxy_gym_backend.models.Exercise;

public interface ExerciseService {
    public Page<Exercise> findAll(Pageable pageable);

    public Exercise findById(Long id);

    public Exercise save(Exercise model);

    Page<Exercise> buscarPorNombre(String nombre, Pageable pageable);
}
