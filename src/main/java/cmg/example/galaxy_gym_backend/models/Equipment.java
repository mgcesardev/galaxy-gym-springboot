package cmg.example.galaxy_gym_backend.models;

import lombok.*;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "equipment")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(unique = true)
    private String serialNumber;

    private String brand;

    private String model;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private EquipmentCategory category;

    private LocalDateTime purchaseDate;

    private LocalDateTime lastMaintenanceDate;

    private LocalDateTime nextMaintenanceDate;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus status = EquipmentStatus.AVAILABLE;

    private String location;

    private Double price;

    private String imageUrl;

    private String maintenanceNotes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum EquipmentCategory {
        CARDIO, STRENGTH, FREE_WEIGHTS, MACHINES,
        FUNCTIONAL, ACCESSORIES, FLOOR_MATS
    }

    public enum EquipmentStatus {
        AVAILABLE, IN_USE, MAINTENANCE, BROKEN, RETIRED
    }
}