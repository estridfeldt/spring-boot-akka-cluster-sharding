package se.estridfeldt.junction.domain;

public class WhereShouldIGo {
    private final Integer junctionId;
    private final Integer containerId;

    public WhereShouldIGo(Integer junctionId, Integer containerId) {
        this.junctionId = junctionId;
        this.containerId = containerId;
    }

    Integer junction() {
        return junctionId;
    }

    Integer container() {
        return containerId;
    }
}
