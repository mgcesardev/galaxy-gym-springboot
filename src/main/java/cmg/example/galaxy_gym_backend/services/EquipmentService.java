package cmg.example.galaxy_gym_backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cmg.example.galaxy_gym_backend.models.Equipment;

public interface EquipmentService {
    public Page<Equipment> findAll(Pageable pageable);

    public Equipment findById(Long id);

    public Equipment save(Equipment model);

    Page<Equipment> buscarPorNombre(String nombre, Pageable pageable);

}
