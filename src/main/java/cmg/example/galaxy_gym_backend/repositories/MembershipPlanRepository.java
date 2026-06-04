package cmg.example.galaxy_gym_backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cmg.example.galaxy_gym_backend.models.MembershipPlan;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {

    @Query("SELECT e FROM MembershipPlan e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<MembershipPlan> buscarPorNombre(@Param("nombre") String nombre, Pageable pageable);

}
