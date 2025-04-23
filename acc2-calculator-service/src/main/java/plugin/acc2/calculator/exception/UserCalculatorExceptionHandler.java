package plugin.acc2.calculator.exception;

import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import plugin.acc2.calculator.dto.*;

@Slf4j
@RestControllerAdvice
public class UserCalculatorExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponseDto handleUnexpectedExceptions(Exception error) {
        log.error(error.getMessage(), error);
        return new ApiErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponseDto handle(InternalServerException error) {
        log.error(error.getMessage(), error);
        return new ApiErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponseDto handle(BadRequestException error) {
        log.error(error.getMessage(), error);
        return new ApiErrorResponseDto(HttpStatus.BAD_REQUEST, error.getMessage());
    }
}
