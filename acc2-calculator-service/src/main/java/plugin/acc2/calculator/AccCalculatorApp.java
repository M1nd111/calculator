package plugin.acc2.calculator;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.*;
import org.springframework.cloud.openfeign.EnableFeignClients;

@ConfigurationPropertiesScan
@EnableFeignClients
@SpringBootApplication
public class AccCalculatorApp {

    public static void main(String[] args) {
        SpringApplication.run(AccCalculatorApp.class, args);
    }

}
