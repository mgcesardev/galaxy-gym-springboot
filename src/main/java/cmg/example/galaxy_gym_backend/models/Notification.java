package cmg.example.galaxy_gym_backend.models;

import lombok.*;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "notifications")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    private String title;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private Boolean isRead = false;

    private LocalDateTime readAt;

    private String link;

    private String icon;

    @Enumerated(EnumType.STRING)
    private NotificationPriority priority = NotificationPriority.MEDIUM;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum NotificationType {
        MEMBERSHIP_EXPIRY, PAYMENT_REMINDER, CLASS_REMINDER,
        PROMOTION, SYSTEM_ALERT, WELCOME, ACCOUNT_UPDATE,
        TRAINER_MESSAGE, ACHIEVEMENT, GENERAL
    }

    public enum NotificationPriority {
        LOW, MEDIUM, HIGH, URGENT
    }
}