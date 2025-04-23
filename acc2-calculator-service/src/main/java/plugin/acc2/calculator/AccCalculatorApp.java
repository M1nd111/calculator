package plugin.acc2.calculator;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.*;

@ConfigurationPropertiesScan
@SpringBootApplication
public class AccCalculatorApp {

    public static void main(String[] args) {
        SpringApplication.run(AccCalculatorApp.class, args);
    }

}
