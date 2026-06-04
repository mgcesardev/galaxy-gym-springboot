package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.Notification;
import cmg.example.galaxy_gym_backend.repositories.NotificationRepository;
import cmg.example.galaxy_gym_backend.services.NotificationService;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Notification> findAll(Pageable pageable) {
        try {
            return notificationRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todas las notificaciones", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Notification findById(Long id) {
        try {
            return notificationRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar notificación con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Notification save(Notification model) {
        try {
            return notificationRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar notificación: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            notificationRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar notificación con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Notification> buscarPorTitulo(String titulo, Pageable pageable) {
        try {
            return notificationRepository.buscarPorTitulo(titulo, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar notificación por titulo: " + e.getMessage());
        }
    }

}
