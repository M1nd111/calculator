package plugin.acc2.calculator.dto;

import java.math.*;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class CalculateRequestDto {

    private BigDecimal amount;

    private int termMonths;

    private Float baseInterestRate;

    private boolean insuranceApplied;

    private Float insuranceDiscount;

    private CreditType creditType;

}
