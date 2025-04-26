package plugin.acc2.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Документ",
    requiredProperties = {
        "id", "name", "isNecessary"
    })
public class DocumentDto {

    @Schema(description = "Id документа", example = "1")
    private Long id;

    @Schema(description = "Название документа", example = "Паспорт")
    private String name;

    @Schema(description = "Обязателен ли документ", example = "true")
    private boolean isNecessary;

}
