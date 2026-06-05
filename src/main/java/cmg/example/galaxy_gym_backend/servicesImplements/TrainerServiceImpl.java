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
        return trainerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Trainer findById(Long id) {
        return trainerRepository.findById(id).orElse(null);
    }

    @Override
    public Trainer save(Trainer model) {
        return trainerRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        trainerRepository.deleteById(id);
    }

}
