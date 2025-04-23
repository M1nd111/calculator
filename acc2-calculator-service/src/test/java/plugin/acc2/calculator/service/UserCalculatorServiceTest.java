package plugin.acc2.calculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import org.junit.jupiter.api.extension.*;
import plugin.acc2.calculator.exception.BadRequestException;
import plugin.acc2.calculator.service.model.CalculateBaseResult;
import plugin.acc2.calculator.service.model.UserCalculation;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static plugin.acc2.calculator.dto.CreditType.CONSUMER_CREDIT;

@ExtendWith(MockitoExtension.class)
class UserCalculationServiceTest {

    @InjectMocks
    private UserCalculatorService userCalculatorService;

    @Mock
    private UserCalculation userCalculation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_calculateLoan_with_valid_data() {
        when(userCalculation.getAmount()).thenReturn(BigDecimal.valueOf(5000000));
        when(userCalculation.getBaseInterestRate()).thenReturn(9.0f);
        when(userCalculation.getInsuranceDiscount()).thenReturn(3.0f);
        when(userCalculation.getTermMonths()).thenReturn(14);
        when(userCalculation.isInsuranceApplied()).thenReturn(true);
        when(userCalculation.getCreditType()).thenReturn(CONSUMER_CREDIT);

        CalculateBaseResult result = userCalculatorService.calculateLoan(userCalculation);

        assertNotNull(result);
        assertEquals(370680.43, result.getMonthlyPayment().doubleValue(), 0.01);
        assertEquals(5189526.02, result.getTotalPayment().doubleValue(), 0.01);
        assertEquals(189526.02, result.getOverpayment().doubleValue(), 0.01);
        assertTrue(result.isInsuranceApplied());
        assertEquals(155685.78, result.getInsuranceDisc().doubleValue(), 0.01);
        assertTrue(result.isInsuranceApplied());
    }

    @Test
    void test_calculateLoan_without_insurance() {
        when(userCalculation.getAmount()).thenReturn(BigDecimal.valueOf(5000000));
        when(userCalculation.getBaseInterestRate()).thenReturn(9.0f);
        when(userCalculation.getInsuranceDiscount()).thenReturn(0.0f);
        when(userCalculation.getTermMonths()).thenReturn(14);
        when(userCalculation.isInsuranceApplied()).thenReturn(false);
        when(userCalculation.getCreditType()).thenReturn(CONSUMER_CREDIT);

        CalculateBaseResult result = userCalculatorService.calculateLoan(userCalculation);

        assertNotNull(result);
        assertEquals(377557.32, result.getMonthlyPayment().doubleValue(), 0.01);
        assertEquals(5285802.48, result.getTotalPayment().doubleValue(), 0.01);
        assertEquals(285802.48, result.getOverpayment().doubleValue(), 0.01);
        assertFalse(result.isInsuranceApplied());
    }

    @Test
    void test_validate_userCalculation_with_invalid_amount() {
        when(userCalculation.getAmount()).thenReturn(BigDecimal.valueOf(-6000000));
        when(userCalculation.getBaseInterestRate()).thenReturn(9.0f);
        when(userCalculation.getInsuranceDiscount()).thenReturn(0.0f);
        when(userCalculation.getTermMonths()).thenReturn(14);
        when(userCalculation.isInsuranceApplied()).thenReturn(false);
        when(userCalculation.getCreditType()).thenReturn(CONSUMER_CREDIT);

        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                userCalculatorService.calculateLoan(userCalculation)
        );
        assertEquals("Неверные параметры запроса", exception.getMessage());
    }

    @Test
    void test_validate_userCalculation_with_invalid_term() {
        when(userCalculation.getAmount()).thenReturn(BigDecimal.valueOf(5000000));
        when(userCalculation.getBaseInterestRate()).thenReturn(9.0f);
        when(userCalculation.getInsuranceDiscount()).thenReturn(0.0f);
        when(userCalculation.getTermMonths()).thenReturn(-9);
        when(userCalculation.isInsuranceApplied()).thenReturn(false);
        when(userCalculation.getCreditType()).thenReturn(CONSUMER_CREDIT);

        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                userCalculatorService.calculateLoan(userCalculation)
        );
        assertEquals("Неверные параметры запроса", exception.getMessage());
    }

    @Test
    void test_validate_userCalculation_with_invalid_interestRate() {
        when(userCalculation.getAmount()).thenReturn(BigDecimal.valueOf(5000000));
        when(userCalculation.getBaseInterestRate()).thenReturn(-9.0f);
        when(userCalculation.getInsuranceDiscount()).thenReturn(0.0f);
        when(userCalculation.getTermMonths()).thenReturn(14);
        when(userCalculation.isInsuranceApplied()).thenReturn(false);
        when(userCalculation.getCreditType()).thenReturn(CONSUMER_CREDIT);

        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                userCalculatorService.calculateLoan(userCalculation)
        );
        assertEquals("Неверные параметры запроса", exception.getMessage());
    }

    @Test
    void test_validate_userCalculation_with_invalid_creditType() {
        when(userCalculation.getAmount()).thenReturn(BigDecimal.valueOf(5000000));
        when(userCalculation.getBaseInterestRate()).thenReturn(9.0f);
        when(userCalculation.getInsuranceDiscount()).thenReturn(0.0f);
        when(userCalculation.getTermMonths()).thenReturn(14);
        when(userCalculation.isInsuranceApplied()).thenReturn(false);
        when(userCalculation.getCreditType()).thenReturn(null);

        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                userCalculatorService.calculateLoan(userCalculation)
        );
        assertEquals("Неверные параметры запроса", exception.getMessage());
    }

}
