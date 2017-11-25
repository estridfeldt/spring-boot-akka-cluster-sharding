package se.estridfeldt.junction.application;

import org.springframework.web.bind.annotation.PathVariable;
import se.estridfeldt.junction.domain.Decisions;
import se.estridfeldt.junction.domain.Go;

public class TargetDecider {

    public Go decide(@PathVariable("junctionId") Integer junctionId, @PathVariable("containerId") Integer containerId) {
        return new Go(Decisions.whereShouldContainerGo(junctionId, containerId));
    }

}
