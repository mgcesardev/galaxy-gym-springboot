package cmg.example.galaxy_gym_backend.models;

import lombok.Data;
import java.math.BigDecimal;
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
