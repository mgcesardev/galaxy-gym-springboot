package cmg.example.galaxy_gym_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cmg.example.galaxy_gym_backend.models.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {


}
