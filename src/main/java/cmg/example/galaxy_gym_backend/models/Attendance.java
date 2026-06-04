package cmg.example.galaxy_gym_backend.models;

import lombok.*;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;

@Entity
@Table(name = "attendances")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    @Enumerated(EnumType.STRING)
    private AttendanceType type = AttendanceType.GYM_ACCESS;

    private String notes;

    private String ipAddress;

    private String deviceInfo;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (checkIn == null) {
            checkIn = LocalDateTime.now();
        }
    }

    public enum AttendanceType {
        GYM_ACCESS, CLASS_ATTENDANCE, TRAINING_SESSION, EVENT
    }
}