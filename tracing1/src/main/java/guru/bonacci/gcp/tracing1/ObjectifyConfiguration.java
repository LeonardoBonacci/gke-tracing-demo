package guru.bonacci.gcp.tracing1;


import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.googlecode.objectify.ObjectifyService;

@Component
public class ObjectifyConfiguration implements ApplicationListener<ApplicationStartedEvent> {

  @Override
  public void onApplicationEvent(ApplicationStartedEvent e) {
    ObjectifyService.init(); 
  }
}
