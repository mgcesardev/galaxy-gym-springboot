package cmg.example.galaxy_gym_backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cmg.example.galaxy_gym_backend.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT e FROM Role e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Role> buscarPorNombre(@Param("nombre") String nombre, Pageable pageable);

    java.util.Optional<Role> findByName(cmg.example.galaxy_gym_backend.models.Role.RoleType name);
}
