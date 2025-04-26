package plugin.acc2.calculator.client;

import org.springframework.cloud.openfeign.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import plugin.acc2.calculator.dto.CreditProductDto;

@FeignClient(
    name = "creditsClient",
    url = "${credits.client.url}"
)
public interface CreditClient {

    @GetMapping(value = "/api/credit-products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreditProductDto> getCreditProduct(@PathVariable Long id);

}
