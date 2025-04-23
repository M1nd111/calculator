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

    private Float baseInterestRate;

    private boolean insuranceApplied;

    private Float insuranceDiscount;

    private CreditType creditType;

}
