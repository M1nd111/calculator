package plugin.acc2.calculator.service.model;

import java.math.*;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class CalculateWithoutInsuranceResult {

    private BigDecimal monthlyPayment;

    private BigDecimal totalPayment;

    private BigDecimal overpayment;

    private Boolean insuranceApplied;

}
