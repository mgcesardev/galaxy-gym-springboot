package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.GymClass;
import cmg.example.galaxy_gym_backend.repositories.GymClassRepository;
import cmg.example.galaxy_gym_backend.services.GymClassService;

@Service
@Transactional
public class GymClassServiceImpl implements GymClassService {

    private final GymClassRepository gymClassRepository;

    public GymClassServiceImpl(GymClassRepository gymClassRepository) {
        this.gymClassRepository = gymClassRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GymClass> findAll(Pageable pageable) {
        return gymClassRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public GymClass findById(Long id) {
        return gymClassRepository.findById(id).orElse(null);
    }

    @Override
    public GymClass save(GymClass model) {
        return gymClassRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        gymClassRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GymClass> buscarPorNombre(String nombre, Pageable pageable) {
        return gymClassRepository.buscarPorNombre(nombre, pageable);
    }

}
