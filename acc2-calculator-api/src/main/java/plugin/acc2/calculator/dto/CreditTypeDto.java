package plugin.acc2.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Schema(description = "Тип кредита с процентной ставкой",
    requiredProperties = {
        "id", "name",
        "baseInterestRate"
    }
)
public class CreditTypeDto {

    @Schema(description = "Id типа кредита", example = "1")
    private Long id;

    @Schema(description = "Название типа кредита", example = "Ипотека")
    private String name;

    @Schema(description = "Базовая процентная ставка", example = "23.9")
    private BigDecimal baseInterestRate;

}
