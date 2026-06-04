package cmg.example.galaxy_gym_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cmg.example.galaxy_gym_backend.models.Trainer;

public interface TrainerService {
    public Page<Trainer> findAll(Pageable pageable);

    public Trainer findById(Long id);

    public Trainer save(Trainer model);


    void delete(Long id);
}
