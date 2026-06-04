package cmg.example.galaxy_gym_backend.servicesImplements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmg.example.galaxy_gym_backend.models.Equipment;
import cmg.example.galaxy_gym_backend.repositories.EquipmentRepository;
import cmg.example.galaxy_gym_backend.services.EquipmentService;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Equipment> findAll(Pageable pageable) {
        try {
            return equipmentRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar todos los equipos", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Equipment findById(Long id) {
        try {
            return equipmentRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar equipo con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Equipment save(Equipment model) {
        try {
            return equipmentRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar equipo: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            equipmentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar equipo con id " + id + ": " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Equipment> buscarPorNombre(String nombre, Pageable pageable) {
        try {
            return equipmentRepository.buscarPorNombre(nombre, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar equipo por nombre: " + e.getMessage());
        }
    }

}
