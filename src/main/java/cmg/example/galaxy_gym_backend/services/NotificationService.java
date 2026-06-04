package cmg.example.galaxy_gym_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cmg.example.galaxy_gym_backend.models.Notification;

public interface NotificationService {
    public Page<Notification> findAll(Pageable pageable);

    public Notification findById(Long id);

    public Notification save(Notification model);

    Page<Notification> buscarPorNombre(String nombre, Pageable pageable);

}
