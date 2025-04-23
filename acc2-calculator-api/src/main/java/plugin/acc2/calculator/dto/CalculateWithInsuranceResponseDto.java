package plugin.acc2.calculator.dto;

import java.math.*;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class CalculateWithInsuranceResponseDto implements LoanResponseDto {

    private BigDecimal monthlyPayment;

    private BigDecimal totalPayment;

    private BigDecimal overpayment;

    private boolean insuranceApplied;

    private BigDecimal insuranceDisc;

}

