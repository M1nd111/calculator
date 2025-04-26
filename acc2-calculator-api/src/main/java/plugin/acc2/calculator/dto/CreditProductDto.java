package plugin.acc2.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Кредитный продукт",
    requiredProperties = {
        "id", "name", "creditType", "insuranceRequired",
        "minAmount", "maxAmount", "minTermMonths", "maxTermMonths",
        "insurances", "documents"
    }
)
public class CreditProductDto {

    @Schema(description = "Id продукта", example = "1")
    private Long id;

    @Schema(description = "Имя продукта", example = "Ипотека - стандарт")
    private String name;

    @Schema(description = "Тип кредита")
    private CreditTypeDto creditType;

    @Schema(description = "Обязательна ли страховка", example = "true")
    private Boolean insuranceRequired;

    @Schema(description = "Минимальная сумма", example = "50000.00")
    private BigDecimal minAmount;

    @Schema(description = "Максимальная сумма", example = "30000000")
    private BigDecimal maxAmount;

    @Schema(description = "Минимальный срок в месяцах", example = "12")
    private Integer minTermMonths;

    @Schema(description = "Максимальный срок в месяцах", example = "240")
    private Integer maxTermMonths;

    @Schema(description = "Список доступных страховок")
    private List<InsuranceDto> insurances;

    @Schema(description = "Список необходимых документов")
    private List<DocumentDto> documents;

}
