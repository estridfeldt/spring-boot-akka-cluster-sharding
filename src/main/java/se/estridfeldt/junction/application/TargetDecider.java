package se.estridfeldt.junction.application;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import se.estridfeldt.junction.domain.Go;
import se.estridfeldt.junction.domain.WhereShouldIGo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static se.estridfeldt.junction.infrastructure.akka.akka.SpringExtension.SPRING_EXTENSION_PROVIDER;

public class TargetDecider {

    private final ActorRef sortingDecider;

    public TargetDecider(ActorSystem system) {
        sortingDecider = system.actorOf(SPRING_EXTENSION_PROVIDER.get(system)
                .props("decidersGuardian"), "decidersGuardian");
    }

    public CompletableFuture<Go> decide(Integer junctionId, Integer containerId) {
        FiniteDuration duration = FiniteDuration.create(1, TimeUnit.DAYS);
        Timeout timeout = Timeout.durationToTimeout(duration);

        Future<Object> result = Patterns.ask(sortingDecider, new WhereShouldIGo(junctionId, containerId), timeout);
        return FutureConverters.toJava(result).toCompletableFuture().thenApply(o -> (Go) o);

    }
}
