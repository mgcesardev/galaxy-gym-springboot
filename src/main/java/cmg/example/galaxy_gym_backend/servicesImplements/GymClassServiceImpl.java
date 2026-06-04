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
        try {
            return gymClassRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todas las clases", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GymClass findById(Long id) {
        try {
            return gymClassRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar clase con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public GymClass save(GymClass model) {
        try {
            return gymClassRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar clase: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            gymClassRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar clase con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GymClass> buscarPorNombre(String nombre, Pageable pageable) {
        try {
            return gymClassRepository.buscarPorNombre(nombre, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar clase por nombre: " + e.getMessage());
        }
    }

}
