package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.Trainer;
import cmg.example.galaxy_gym_backend.repositories.TrainerRepository;
import cmg.example.galaxy_gym_backend.services.TrainerService;

@Service
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Trainer> findAll(Pageable pageable) {
        try {
            return trainerRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todos los entrenadores", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Trainer findById(Long id) {
        try {
            return trainerRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar entrenador con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Trainer save(Trainer model) {
        try {
            return trainerRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar entrenador: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            trainerRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar entrenador con id " + id + ": " + e.getMessage());
        }
    }

    

}
