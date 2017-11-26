package se.estridfeldt.junction;

import akka.actor.ExtendedActorSystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.estridfeldt.junction.application.TargetDecider;

@SpringBootApplication
public class JunctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(JunctionApplication.class, args);
    }

    @Bean
    public TargetDecider targetDecider(ExtendedActorSystem system) {
        return new TargetDecider(system);
    }
}
