package guru.bonacci.gcp.tracing2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@SpringBootApplication
public class Tracing2Application {

	public static void main(String[] args) {
		SpringApplication.run(Tracing2Application.class, args);
	}
	
	@GetMapping("/{input}") 
  public ResponseEntity<String> echo(@PathVariable String input) {
      return new ResponseEntity<String>("echo 2 " + input, HttpStatus.OK);
  }
}
