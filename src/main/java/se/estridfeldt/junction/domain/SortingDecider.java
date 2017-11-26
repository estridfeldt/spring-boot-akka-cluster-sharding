package se.estridfeldt.junction.domain;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SortingDecider extends AbstractActor {

    public static final String NAME = "sortingDecider";
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return new ReceiveBuilder()
                .match(WhereShouldIGo.class, m -> {
                    String decision = Decisions.whereShouldContainerGo(m.junction(), m.container());
                    log.info("Decision on junction {} for container {}: {}", m.junction(), m.container(), decision);
                    getSender().tell(new Go(decision), getSelf());
                })
                .matchAny(o -> log.info("unknown message"))
                .build();
    }
}
