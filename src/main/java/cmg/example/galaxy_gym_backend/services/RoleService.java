package cmg.example.galaxy_gym_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cmg.example.galaxy_gym_backend.models.Role;

public interface RoleService {
    public Page<Role> findAll(Pageable pageable);

    public Role findById(Long id);

    public Role save(Role model);

    Page<Role> buscarPorNombre(String nombre, Pageable pageable);
    

}
