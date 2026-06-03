package cmg.example.galaxy_gym_backend.DTO;

import lombok.Data;
import java.math.BigDecimal;

import cmg.example.galaxy_gym_backend.models.Membership;
import jakarta.validation.constraints.*;

@Data
public class MembershipPurchaseDTO {
    @NotNull
    private Long userId;

    @NotNull
    private Long planId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Membership.PaymentMethod paymentMethod;

    private String couponCode;
}
