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
        return equipmentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Equipment findById(Long id) {
        return equipmentRepository.findById(id).orElse(null);
    }

    @Override
    public Equipment save(Equipment model) {
        return equipmentRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Equipment> buscarPorNombre(String nombre, Pageable pageable) {
        return equipmentRepository.buscarPorNombre(nombre, pageable);
    }

}
