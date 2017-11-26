package se.estridfeldt.junction.domain;

import java.io.Serializable;

public class Go implements Serializable {
    private final String targetConveyor;

    public Go(String targetConveyor) {

        this.targetConveyor = targetConveyor;
    }

    public String getTargetConveyor() {
        return targetConveyor;
    }
}
