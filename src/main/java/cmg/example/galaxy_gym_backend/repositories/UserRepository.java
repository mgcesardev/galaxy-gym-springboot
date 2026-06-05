package cmg.example.galaxy_gym_backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cmg.example.galaxy_gym_backend.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT e FROM User e WHERE LOWER(e.username) LIKE LOWER(CONCAT('%', :username, '%'))")
    Page<User> buscarPorNombre(@Param("nombre") String nombre, Pageable pageable);

    @Query("SELECT e FROM User e WHERE LOWER(e.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    Page<User> buscarPorCorreo(@Param("correo") String correo, Pageable pageable);

    Optional<User> findByEmail(String email);

}
