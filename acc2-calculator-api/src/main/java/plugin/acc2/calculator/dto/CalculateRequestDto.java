package plugin.acc2.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class CalculateRequestDto {

    private BigDecimal amount;

    private int termMonths;

    private BigDecimal baseInterestRate;

    private boolean insuranceApplied;

    private BigDecimal insuranceDiscount;

    private CreditType creditType;

}
