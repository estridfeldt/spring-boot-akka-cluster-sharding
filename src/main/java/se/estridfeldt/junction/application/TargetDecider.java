package se.estridfeldt.junction.application;

import akka.actor.ActorRef;
import akka.actor.ExtendedActorSystem;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ClusterShardingSettings;
import akka.cluster.sharding.ShardRegion;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import se.estridfeldt.junction.domain.Go;
import se.estridfeldt.junction.domain.SortingDecider;
import se.estridfeldt.junction.domain.WhereShouldIGo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static se.estridfeldt.junction.infrastructure.akka.akka.SpringExtension.SPRING_EXTENSION_PROVIDER;

public class TargetDecider {

    private final ActorRef sortingDecider;

    public TargetDecider(ExtendedActorSystem system) {

        ClusterSharding clusterSharding = new ClusterSharding(system);
        Props sortingDecider = SPRING_EXTENSION_PROVIDER.get(system)
                .props("sortingDecider");
        clusterSharding.start(SortingDecider.NAME, sortingDecider, ClusterShardingSettings.apply(system), new ShardRegion.MessageExtractor() {
            @Override
            public String shardId(Object message) {
                if (message instanceof WhereShouldIGo) {
                    return Integer.toString(((WhereShouldIGo) message).junction() % 2);
                }
                return null;
            }

            @Override
            public String entityId(Object message) {
                if (message instanceof WhereShouldIGo) {
                    return ((WhereShouldIGo) message).junction().toString();
                }
                return null;
            }

            @Override
            public Object entityMessage(Object message) {
                return message;
            }
        });

        this.sortingDecider = clusterSharding.shardRegion(SortingDecider.NAME);
    }

    public CompletableFuture<Go> decide(Integer junctionId, Integer containerId) {
        FiniteDuration duration = FiniteDuration.create(1, TimeUnit.SECONDS);
        Timeout timeout = Timeout.durationToTimeout(duration);

        Future<Object> result = Patterns.ask(sortingDecider, new WhereShouldIGo(junctionId, containerId), timeout);
        return FutureConverters.toJava(result).toCompletableFuture().thenApply(o -> (Go) o);

    }
}
