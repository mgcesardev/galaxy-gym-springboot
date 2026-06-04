package cmg.example.galaxy_gym_backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cmg.example.galaxy_gym_backend.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT e FROM Notification e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<Notification> buscarPorTitulo(@Param("titulo") String titulo, Pageable pageable);

}
