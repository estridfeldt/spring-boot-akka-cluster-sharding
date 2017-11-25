package se.estridfeldt.junction.domain;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

import static se.estridfeldt.junction.infrastructure.akka.akka.SpringExtension.SPRING_EXTENSION_PROVIDER;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DecidersGuardian extends AbstractActor {

    @Override
    public Receive createReceive() {
        return new ReceiveBuilder()
                .match(WhereShouldIGo.class, m -> {
                    String name = "J" + m.junction();
                    ActorRef worker = getContext()
                            .findChild(name)
                            .orElseGet(createChild(name));
                    worker.forward(m, getContext());
                })
                .build();
    }

    private Supplier<ActorRef> createChild(String name) {
        return () -> {
            Props props = SPRING_EXTENSION_PROVIDER.get(getContext().getSystem()).props("sortingDecider");
            return getContext().actorOf(props, name);
        };
    }

}
