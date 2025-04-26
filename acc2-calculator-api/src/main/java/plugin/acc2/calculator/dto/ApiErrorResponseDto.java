package plugin.acc2.calculator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

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
