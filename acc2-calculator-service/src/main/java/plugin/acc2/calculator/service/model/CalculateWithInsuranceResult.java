package plugin.acc2.calculator.service.model;

import java.math.*;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class CalculateWithInsuranceResult {

    private BigDecimal monthlyPayment;

    private BigDecimal totalPayment;

    private BigDecimal overpayment;

    private Boolean insuranceApplied;

    private BigDecimal insuranceDisc;

}

