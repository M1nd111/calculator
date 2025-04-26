package plugin.acc2.calculator.api;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.*;
import org.springframework.web.*;
import org.springframework.web.bind.annotation.*;

import plugin.acc2.calculator.dto.*;

@RequestMapping("/api/calculator")
@SecurityRequirement(name = "BearerAuth")
public interface CalculatorApi {

    @GetMapping
    @Operation(
            summary = "Расчет платежей с учетом страховки",
            description = "Возвращает расчет ежемесячного платежа, полной суммы кредита и переплаты"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный расчет параметров",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            oneOf = {
                                                    CalculateWithoutInsuranceResponseDto.class,
                                                    CalculateWithInsuranceResponseDto.class
                                            }
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверные параметры запроса",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "type": "RETRY",
                                                                "code": 1004,
                                                                "message": "Некорректные параметры"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера. Ошибка вычислений)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "type": "FINAL",
                                                                "code": 1015,
                                                                "message": "Ошибка внутри алгоритма расчёта"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    ResponseEntity<LoanResponseDto> calculateLoan(
            @ModelAttribute CalculateRequestDto calculateRequestDto
    );

}
