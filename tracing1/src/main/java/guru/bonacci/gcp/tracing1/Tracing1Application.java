package guru.bonacci.gcp.tracing1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@SpringBootApplication
public class Tracing1Application {

  @Value("${service.two}") String two;
  
	public static void main(String[] args) {
		SpringApplication.run(Tracing1Application.class, args);
	}

  @GetMapping("/one/{input}") 
  public ResponseEntity<String> echo(@PathVariable String input) {
      return new ResponseEntity<String>("echo 1 " + input, HttpStatus.OK);
  }
  
  @GetMapping("/two/{input}") 
  public Mono<ResponseEntity<String>> echo2(@PathVariable String input) {
    System.out.println(two);
    return WebClient.create(two).get().uri("/api/" + input).retrieve().bodyToMono(String.class)
          .map(responseBody -> new ResponseEntity<>(responseBody, HttpStatus.ACCEPTED));
  }
}
