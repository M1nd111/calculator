package plugin.acc2.calculator.dto;

import java.math.*;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class CalculateWithoutInsuranceResponseDto  implements LoanResponseDto{

    private BigDecimal monthlyPayment;

    private BigDecimal totalPayment;

    private BigDecimal overpayment;

    private Boolean insuranceApplied;

}
