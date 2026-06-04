package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.User;
import cmg.example.galaxy_gym_backend.repositories.UserRepository;
import cmg.example.galaxy_gym_backend.services.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        try {
            return userRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todos los usuarios", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        try {
            return userRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar usuario con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public User save(User model) {
        try {
            return userRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar usuario: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar usuario con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Page<User> buscarPorNombre(String nombre, Pageable pageable) {
        try {
            return userRepository.buscarPorNombre(nombre, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar usuario por nombre: " + e.getMessage());
        }
    }

    @Override
    public Page<User> buscarPorCorreo(String correo, Pageable pageable) {
        try {
            return userRepository.buscarPorCorreo(correo, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar usuario por correo: " + e.getMessage());
        }
    }

}
