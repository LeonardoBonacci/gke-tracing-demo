package guru.bonacci.gcp.tracing2;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@SpringBootApplication
public class BackendApp {

  private static final Logger log = LoggerFactory.getLogger(BackendApp.class);

  
	public static void main(String[] args) {
		SpringApplication.run(BackendApp.class, args);
	}
	
  @GetMapping("/echo/{input}") 
  public String echo(@PathVariable String input,
                    @RequestHeader Map<String, String> headers) {
    log.info("frontend incoming = outgoing {}", input);
    return input;
  }
}
