package plugin.acc2.calculator.service.model;

import java.math.*;

import lombok.*;
import plugin.acc2.calculator.dto.CreditType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCalculation {

    private BigDecimal amount;

    private int termMonths;

    private BigDecimal baseInterestRate;

    private boolean insuranceApplied;

    private BigDecimal insuranceDiscount;

    private CreditType creditType;

}
