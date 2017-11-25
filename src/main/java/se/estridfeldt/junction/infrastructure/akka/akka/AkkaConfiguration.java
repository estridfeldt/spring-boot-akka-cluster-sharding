package se.estridfeldt.junction.infrastructure.akka.akka;

import akka.actor.ActorSystem;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static se.estridfeldt.junction.infrastructure.akka.akka.SpringExtension.SPRING_EXTENSION_PROVIDER;

@Configuration
public class AkkaConfiguration {

    @Bean
    public ActorSystem actorSystem(ApplicationContext applicationContext) {
        ActorSystem system = ActorSystem.create("akka-spring-demo");
        SPRING_EXTENSION_PROVIDER.get(system)
                .initialize(applicationContext);
        return system;
    }
}
