package se.estridfeldt.junction.infrastructure.akka.akka;

import akka.actor.ActorSystem;
import akka.actor.ExtendedActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static se.estridfeldt.junction.infrastructure.akka.akka.SpringExtension.SPRING_EXTENSION_PROVIDER;

@Configuration
public class AkkaConfiguration {

    @Bean
    public ExtendedActorSystem actorSystem(ApplicationContext applicationContext) {
        Config config = config();
        String applicationName = config.getConfig("application").getString("name");
        ActorSystem system = ActorSystem.create(applicationName, config);
        SPRING_EXTENSION_PROVIDER.get(system)
                .initialize(applicationContext);
        return (ExtendedActorSystem) system;
    }

    private Config config() {
        return ConfigFactory.load();
    }
}
