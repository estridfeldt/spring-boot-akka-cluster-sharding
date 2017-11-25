package se.estridfeldt.junction.domain;

public class Go {
    private final String targetConveyor;

    public Go(String targetConveyor) {

        this.targetConveyor = targetConveyor;
    }

    public String getTargetConveyor() {
        return targetConveyor;
    }
}
