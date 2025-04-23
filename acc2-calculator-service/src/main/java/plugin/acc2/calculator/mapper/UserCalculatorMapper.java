package plugin.acc2.calculator.mapper;

import org.mapstruct.*;

import plugin.acc2.calculator.dto.*;
import plugin.acc2.calculator.service.model.*;

@Mapper(componentModel = "spring")
public interface UserCalculatorMapper {

    UserCalculation toModel(CalculateRequestDto calculateRequestDto);

    CalculateWithoutInsuranceResponseDto toWithoutInsuranceDto(CalculateBaseResult calculateBaseResult);

    CalculateWithInsuranceResponseDto toWithInsuranceDto(CalculateBaseResult calculateBaseResult);

}
