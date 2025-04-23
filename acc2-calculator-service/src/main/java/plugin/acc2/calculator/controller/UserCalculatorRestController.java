package plugin.acc2.calculator.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import plugin.acc2.calculator.api.*;
import plugin.acc2.calculator.dto.*;
import plugin.acc2.calculator.mapper.*;
import plugin.acc2.calculator.service.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserCalculatorRestController implements CalculatorApi {

    private final UserCalculatorMapper userCalculatorMapper;

    private final UserCalculatorService userCalculatorService;

    @Override
    public ResponseEntity<LoanResponseDto> calculateLoan(
        CalculateRequestDto calculateRequestDto
    ) {
        var userCalculator = userCalculatorMapper.toModel(calculateRequestDto);
        var calculationResult = userCalculatorService.calculateLoan(userCalculator);

        if (calculationResult.isInsuranceApplied()) {
            var calculateWithInsuranceResponseDto = userCalculatorMapper
                .toWithInsuranceDto(calculationResult);
            return ResponseEntity.ok().body(calculateWithInsuranceResponseDto);
        } else {
            var calculateWithoutInsuranceDto = userCalculatorMapper
                .toWithoutInsuranceDto(calculationResult);
            return ResponseEntity.ok().body(calculateWithoutInsuranceDto);
        }
    }

}
