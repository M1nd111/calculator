package plugin.acc2.calculator.dto;

import java.time.*;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.http.*;

@Getter
public class ApiErrorResponseDto {

    private final HttpStatus status;

    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time = LocalDateTime.now();

    public ApiErrorResponseDto(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
