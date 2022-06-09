package guru.bonacci.gcp.tracing1;

import java.time.Instant;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.instrument.async.ExecutorConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.googlecode.objectify.ObjectifyService;

import brave.Tracing;


@RestController
@RequestMapping("/api")
@SpringBootApplication
@Import({ ExecutorConfiguration.class })
public class FrontendApp {

  private static final Logger log = LoggerFactory.getLogger(FrontendApp.class);
  
//  @Autowired RestTemplate restTemplate;
  @Value("${service.two}") String backend;
  @Autowired Tracing tracing;
  @Autowired ThreadPoolTaskExecutor executor;
  @Autowired ThreadPoolTaskScheduler scheduler;
  
	public static void main(String[] args) {
		SpringApplication.run(FrontendApp.class, args);
	}

  @GetMapping("/echo/{input}") 
  public String echo(@PathVariable String input) throws InterruptedException, ExecutionException {
    log.info("frontend incoming = outgoing {}", input);
    
    embedded();
    Thread.sleep(2000);
    return input;
  }
  
  void embedded() throws InterruptedException, ExecutionException {
    log.info("log something out");

    Instant at = Instant.now().plusMillis(1);
    
    Runnable runExec = () -> {
      log.info("log something executed");
      try {
      
        ObjectifyService.run(() -> {
          log.info("log something in executed");
          return true;
        });

      } catch (Exception e) {
        log.error(e.getLocalizedMessage());
      }
    };

    Runnable runSched = () -> {
      log.info("log something scheduled");
      try {
      
        ObjectifyService.run(() -> {
          log.info("log something in scheduled");
          return true;
        });

      } catch (Exception e) {
        log.error(e.getLocalizedMessage());
      }
      
    };

//    tracing.currentTraceContext().wrap(runMe).run();
    executor.execute(runExec);
    scheduler.schedule(runSched, at);
    Thread.sleep(1000);
  }

  
//  @GetMapping("/passon/{input}") 
//  public String echo2(@PathVariable String input) throws IOException, InterruptedException {
//    log.info("frontend incoming {}", input);
////    var result = restTemplate.getForObject(backend + "/api/echo/" + input, String.class);
//
////    Span span = tracer.getCurrentSpan();
//    HttpClient httpClient = HttpClient.newBuilder().build();
//    HttpRequest request = HttpRequest.newBuilder()
//        .uri(URI.create(backend + "/api/echo/" + input))
////        .header("x-b3-traceid", span.getContext().getTraceId().toString())
////        .header("x-b3-parentspanid", String.valueOf(span.getContext().getSpanId()))
//        .header("x-b3-sampled", "1")
//        .build();
//
//    var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
//    var result = response.body();
//
//    log.info("frontend outgoing {}", result);
//    return result;
//  }
//
//  @Bean RestTemplate restTemplate() {
//    return new RestTemplate();
//  }
}
