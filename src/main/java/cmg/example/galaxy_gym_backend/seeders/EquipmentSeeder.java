package cmg.example.galaxy_gym_backend.seeders;

import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import cmg.example.galaxy_gym_backend.models.Equipment;
import cmg.example.galaxy_gym_backend.models.Equipment.EquipmentCategory;
import cmg.example.galaxy_gym_backend.models.Equipment.EquipmentStatus;
import cmg.example.galaxy_gym_backend.repositories.EquipmentRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@Order(3)
public class EquipmentSeeder implements CommandLineRunner {

    private final EquipmentRepository equipmentRepository;

    public EquipmentSeeder(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (equipmentRepository.count() == 0) {
            log.info("Seeding initial equipment...");
            List<Equipment> equipmentList = Arrays.asList(
                new Equipment(null, "Caminadora Pro Run", "TR-100234", "LifeFitness", "T9", "Caminadora profesional de alta gama", EquipmentCategory.CARDIO, LocalDateTime.now().minusMonths(6), LocalDateTime.now().minusMonths(1), LocalDateTime.now().plusMonths(2), EquipmentStatus.AVAILABLE, "Zona Cardio 1", 2500.0, "https://example.com/img/treadmill", "Mantenimiento preventivo al día", null, null),
                new Equipment(null, "Bicicleta de Spinning", "SP-230911", "Matrix", "IC7", "Bicicleta magnética para indoor cycling", EquipmentCategory.CARDIO, LocalDateTime.now().minusMonths(4), LocalDateTime.now().minusWeeks(2), LocalDateTime.now().plusWeeks(6), EquipmentStatus.AVAILABLE, "Salón de Ciclo", 1200.0, "https://example.com/img/spin-bike", "Ajuste de pedal y freno realizado", null, null),
                new Equipment(null, "Banco Plano Multiposición", "BE-9031", "Hammer Strength", "Multi-Bench", "Banco para press ajustable", EquipmentCategory.STRENGTH, LocalDateTime.now().minusYears(1), LocalDateTime.now().minusMonths(3), LocalDateTime.now().plusMonths(3), EquipmentStatus.AVAILABLE, "Zona de Pesas", 600.0, "https://example.com/img/bench", "Estructura estable", null, null),
                new Equipment(null, "Mancuernas de Uretano 20kg", "DB-20K", "Rogue", "Urethane", "Par de mancuernas de 20kg", EquipmentCategory.FREE_WEIGHTS, LocalDateTime.now().minusMonths(8), null, null, EquipmentStatus.AVAILABLE, "Zona de Mancuernas", 180.0, "https://example.com/img/dumbbell", "Sin daños", null, null)
            );
            equipmentRepository.saveAll(equipmentList);
            log.info("Successfully seeded {} equipment.", equipmentList.size());
        } else {
            log.info("Equipment table already has data. Skipping seeding.");
        }
    }
}
