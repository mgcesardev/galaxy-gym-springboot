package cmg.example.galaxy_gym_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cmg.example.galaxy_gym_backend.models.GymClass;


public interface GymClassService {
    public Page<GymClass> findAll(Pageable pageable);

    public GymClass findById(Long id);

    public GymClass save(GymClass model);

    Page<GymClass> buscarPorNombre(String nombre, Pageable pageable);

}
