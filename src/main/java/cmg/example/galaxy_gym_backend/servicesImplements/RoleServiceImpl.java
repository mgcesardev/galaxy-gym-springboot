package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.Role;
import cmg.example.galaxy_gym_backend.repositories.RoleRepository;
import cmg.example.galaxy_gym_backend.services.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Role> findAll(Pageable pageable) {
        try {
            return roleRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todos los roles", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Role findById(Long id) {
        try {
            return roleRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar rol con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Role save(Role model) {
        try {
            return roleRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar rol: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar rol con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Role> buscarPorNombre(String nombre, Pageable pageable) {
        try {
            return roleRepository.buscarPorNombre(nombre, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar rol por nombre: " + e.getMessage());
        }
    }

}
