package cmg.example.galaxy_gym_backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cmg.example.galaxy_gym_backend.models.WorkoutRoutine;

public interface WorkoutRoutineRepository extends JpaRepository<WorkoutRoutine, Long> {

    @Query("SELECT e FROM WorkoutRoutine e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<WorkoutRoutine> buscarPorNombre(@Param("nombre") String nombre, Pageable pageable);

}
