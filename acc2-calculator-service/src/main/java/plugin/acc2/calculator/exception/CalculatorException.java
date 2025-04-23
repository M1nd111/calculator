package plugin.acc2.calculator.exception;

import lombok.Getter;

@Getter
public class CalculatorException extends RuntimeException {

    public CalculatorException(String message) {
        super(message);
    }

}
