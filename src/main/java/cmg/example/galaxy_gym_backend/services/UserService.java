package cmg.example.galaxy_gym_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cmg.example.galaxy_gym_backend.models.User;

public interface UserService {
    public Page<User> findAll(Pageable pageable);

    public User findById(Long id);

    public User save(User model);

    Page<User> buscarPorNombre(String nombre, Pageable pageable);
}
