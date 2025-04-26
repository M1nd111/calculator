package plugin.acc2.calculator.service;

import java.math.*;

import lombok.*;
import org.springframework.stereotype.*;
import plugin.acc2.calculator.client.CreditClient;
import plugin.acc2.calculator.exception.BadRequestException;
import plugin.acc2.calculator.service.model.*;

@Service
@RequiredArgsConstructor
public class UserCalculatorService {

    private final CreditClient creditClient;

    public CalculateBaseResult calculateLoan(UserCalculation userCalculation) {

        validateUserCalculation(userCalculation);

        BigDecimal discountCost = null;
        boolean insurance = userCalculation.isInsuranceApplied();
        BigDecimal amount = userCalculation.getAmount();
        BigDecimal interestRate = userCalculation.getBaseInterestRate();
        BigDecimal insuranceDiscount = userCalculation.getInsuranceDiscount();

        if (insuranceDiscount.compareTo(BigDecimal.ZERO) != 0) {
            interestRate = interestRate.subtract(insuranceDiscount);
        }

        int termMonths = userCalculation.getTermMonths();

        BigDecimal annuityPayment = calculateAnnuityPayment(amount, interestRate, termMonths);
        BigDecimal totalPayment = annuityPayment.multiply(BigDecimal.valueOf(termMonths))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal overpayment = totalPayment.subtract(amount).setScale(
                2,
                RoundingMode.HALF_UP);

        if (insurance) {
            BigDecimal discountRate = userCalculation.getInsuranceDiscount();
            discountCost = totalPayment
                    .multiply(discountRate
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

    /**
     * Расчёт аннуитетного ежемесячного платежа по формуле:
     * <p>
     * A = P * (r * (1 + r)^n) / ((1 + r)^n - 1)
     * <p>
     * где:
     * A — ежемесячный платеж (аннуитет),
     * P — сумма кредита (amount),
     * r — месячная процентная ставка (годовая ставка / 12 / 100),
     * n — срок кредита в месяцах (termMonths).
     *
     * @param amount             сумма кредита
     * @param interestRateAnnual годовая процентная ставка, например 9.0f означает 9%
     * @param termMonths         срок кредита в месяцах
     * @return аннуитетный ежемесячный платеж, округлённый до 2 знаков после запятой
     */
    private BigDecimal calculateAnnuityPayment(
            BigDecimal amount,
            BigDecimal interestRateAnnual,
            Integer termMonths
    ) {
        BigDecimal monthlyRate = interestRateAnnual
                .divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);

        BigDecimal onePlusRate = monthlyRate.add(BigDecimal.ONE);
        BigDecimal power = onePlusRate.pow(termMonths, MathContext.DECIMAL64);
        BigDecimal numerator = monthlyRate.multiply(power);
        BigDecimal denominator = power.subtract(BigDecimal.ONE);

        BigDecimal result = amount.multiply(numerator)
                .divide(denominator, 10, RoundingMode.HALF_UP);
        return result.setScale(2, RoundingMode.HALF_UP);
    }

    private void validateUserCalculation(UserCalculation userCalculation) {
        var amount = userCalculation.getAmount();
        var termMonths = userCalculation.getTermMonths();
        var interestRate = userCalculation.getBaseInterestRate();
        var creditType = userCalculation.getCreditType();
        var insuranceApplied = userCalculation.isInsuranceApplied();
        var insuranceDiscount = userCalculation.getInsuranceDiscount();

        if (insuranceApplied && insuranceDiscount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Неверные параметры запроса");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0 || termMonths <= 0
                || interestRate.compareTo(BigDecimal.ZERO) <= 0 || creditType == null) {
            throw new BadRequestException("Неверные параметры запроса");
        }

        validateCreditTypes(creditType.getId(), amount, termMonths);

    }

    private void validateCreditTypes(Long idFk, BigDecimal amount, Integer termMonths) {
        var credit = creditClient.getCreditProduct(idFk).getBody();

        if (credit == null) {
            throw new
                    BadRequestException("Внутренняя ошибка сервера");
        }

        var minAmount = credit.getMinAmount();
        var maxAmount = credit.getMaxAmount();
        var minTermMonth = credit.getMinTermMonths();
        var maxTermMonth = credit.getMaxTermMonths();

        if (amount.compareTo(minAmount) < 0 || amount.compareTo(maxAmount) > 0 ||
                termMonths < minTermMonth || termMonths > maxTermMonth) {
            throw new BadRequestException("Параметры не соответствуют требованиям для типа кредита");
        }
    }
}
