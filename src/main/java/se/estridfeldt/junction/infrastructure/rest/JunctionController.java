package se.estridfeldt.junction.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.estridfeldt.junction.application.TargetDecider;

@RestController
@RequestMapping("/junctions")
public class JunctionController {

    private final TargetDecider targetDecider;

    public JunctionController(TargetDecider targetDecider) {
        this.targetDecider = targetDecider;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{junctionId}/decisionForContainer/{containerId}")
    public ResponseEntity decisionForContainer(@PathVariable("junctionId") Integer junctionId, @PathVariable("containerId") Integer containerId) {
        return ResponseEntity.ok(targetDecider.decide(junctionId, containerId));
    }
}
