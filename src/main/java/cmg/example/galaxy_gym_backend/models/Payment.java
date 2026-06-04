package cmg.example.galaxy_gym_backend.models;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import cmg.example.galaxy_gym_backend.models.Membership.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "payments")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "membership_id", nullable = false)
    private Membership membership;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal amount;

    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.COMPLETED;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private String transactionId;

    private String receiptNumber;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String paymentProof;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }
        if (receiptNumber == null) {
            receiptNumber = "REC-" + System.currentTimeMillis();
        }
    }

    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED, REFUNDED, CANCELLED
    }
}