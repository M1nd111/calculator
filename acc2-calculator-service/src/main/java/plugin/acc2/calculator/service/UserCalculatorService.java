package plugin.acc2.calculator.service;

import java.math.*;
import lombok.*;
import org.springframework.stereotype.*;
import plugin.acc2.calculator.exception.BadRequestException;
import plugin.acc2.calculator.service.model.*;

@Service
@RequiredArgsConstructor
public class UserCalculatorService {

    public CalculateBaseResult calculateLoan(UserCalculation userCalculation) {

        validateuserCalculation(userCalculation);

        BigDecimal discountCost = null;
        boolean insurance = userCalculation.isInsuranceApplied();
        BigDecimal amount = userCalculation.getAmount();
        Float interestRate = userCalculation.getBaseInterestRate() - userCalculation.getInsuranceDiscount();
        int termMonths = userCalculation.getTermMonths();

        BigDecimal annuityPayment = calculateAnnuityPayment(amount, interestRate, termMonths);
        BigDecimal totalPayment = annuityPayment.multiply(BigDecimal.valueOf(termMonths))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal overpayment = totalPayment.subtract(amount).setScale(
                2,
                RoundingMode.HALF_UP);

        if (insurance) {
            Float discountRate = userCalculation.getInsuranceDiscount();
            discountCost = totalPayment
                    .multiply(BigDecimal.valueOf(discountRate)
                            .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                    ).setScale(2, RoundingMode.HALF_UP);

        }
        return new CalculateBaseResult(
                annuityPayment,
                totalPayment,
                overpayment,
                insurance,
                discountCost);
    }

    private BigDecimal calculateAnnuityPayment(
            BigDecimal amount,
            Float interestRateAnnual,
            Integer termMonths
    ) {
        BigDecimal monthlyRate = BigDecimal.valueOf(interestRateAnnual)
                .divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);

        BigDecimal onePlusRate = monthlyRate.add(BigDecimal.ONE);
        BigDecimal power = onePlusRate.pow(termMonths, MathContext.DECIMAL64);
        BigDecimal numerator = monthlyRate.multiply(power);
        BigDecimal denominator = power.subtract(BigDecimal.ONE);

        BigDecimal result = amount.multiply(numerator)
                .divide(denominator, 10, RoundingMode.HALF_UP);
        return result.setScale(2, RoundingMode.HALF_UP);
    }

    private void validateuserCalculation(UserCalculation userCalculation) {
        var amount = userCalculation.getAmount();
        var termMonths = userCalculation.getTermMonths();
        var interestRate = userCalculation.getBaseInterestRate();
        var creditType = userCalculation.getCreditType();
        var insuranceApplied = userCalculation.isInsuranceApplied();
        var insuranceDiscount = userCalculation.getInsuranceDiscount();

        if (insuranceApplied && insuranceDiscount < 0) {
            throw new BadRequestException("Неверные параметры запроса");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0 || termMonths <= 0 || interestRate <= 0 || creditType == null) {
            throw new BadRequestException("Неверные параметры запроса");
        }

        switch (creditType) {
            case CONSUMER_CREDIT -> {
                if (amount.compareTo(BigDecimal.valueOf(50_000)) < 0 ||
                        amount.compareTo(BigDecimal.valueOf(5_000_000)) > 0 ||
                        termMonths < 12 || termMonths > 60) {
                    throw new
                            BadRequestException("Параметры не соответствуют требованиям для потребительского кредита");
                }
            }
            case MORTGAGE -> {
                if (amount.compareTo(BigDecimal.valueOf(1_000_000)) < 0 ||
                        amount.compareTo(BigDecimal.valueOf(30_000_000)) > 0 ||
                        termMonths < 60 || termMonths > 240) {
                    throw new BadRequestException("Параметры не соответствуют требованиям для ипотеки");
                }
            }
            case CAR_CREDIT -> {
                if (amount.compareTo(BigDecimal.valueOf(500_000)) < 0 ||
                        amount.compareTo(BigDecimal.valueOf(15_000_000)) > 0 ||
                        termMonths < 36 || termMonths > 120) {
                    throw new BadRequestException("Параметры не соответствуют требованиям для автокредита");
                }
            }
            default -> {
            }
        }

    }

}
