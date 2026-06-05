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
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User model) {
        return userRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> buscarPorNombre(String nombre, Pageable pageable) {
        return userRepository.buscarPorNombre(nombre, pageable);
    }

    @Override
    public Page<User> buscarPorCorreo(String correo, Pageable pageable) {
        return userRepository.buscarPorCorreo(correo, pageable);
    }

}
