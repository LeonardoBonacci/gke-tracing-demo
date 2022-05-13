package guru.bonacci.gcp.tracing1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@SpringBootApplication
public class FrontendApp {

  private static final Logger log = LoggerFactory.getLogger(FrontendApp.class);
  
  @Autowired RestTemplate restTemplate;
  @Value("${service.two}") String backend;
  
	public static void main(String[] args) {
		SpringApplication.run(FrontendApp.class, args);
	}

  @GetMapping("/echo/{input}") 
  public String echo(@PathVariable String input) {
    log.info("frontend incoming = outgoing {}", input);
    return input;
  }
  
  @GetMapping("/passon/{input}") 
  public String echo2(@PathVariable String input) {
    log.info("frontend incoming {}", input);
    var result = restTemplate.getForObject(backend + "/api/echo/" + input, String.class);
    log.info("frontend outgoing {}", result);
    return result;
  }
  
  @Bean RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
