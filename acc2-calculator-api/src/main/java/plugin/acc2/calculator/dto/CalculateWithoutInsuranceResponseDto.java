package plugin.acc2.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class CalculateWithoutInsuranceResponseDto  implements LoanResponseDto{

    private BigDecimal monthlyPayment;

    private BigDecimal totalPayment;

    private BigDecimal overpayment;

    private Boolean insuranceApplied;

}
