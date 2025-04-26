package plugin.acc2.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Страховка",
    requiredProperties = {
        "id", "name",
        "insuranceImpact",
        "providerName"
    }
)
public class InsuranceDto {

    @Schema(description = "Id страховки", example = "1")
    private Long id;

    @Schema(description = "Название страховки", example = "Имущество")
    private String name;

    @Schema(description = "Влияние страховки", example = "5")
    private BigDecimal insuranceImpact;

    @Schema(description = "Название страховой компании", example = "Росгосстрах")
    private String providerName;

}
