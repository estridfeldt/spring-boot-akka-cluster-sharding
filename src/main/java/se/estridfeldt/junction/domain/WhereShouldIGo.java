package se.estridfeldt.junction.domain;

import java.io.Serializable;

public class WhereShouldIGo implements Serializable {
    private final Integer junctionId;
    private final Integer containerId;

    public WhereShouldIGo(Integer junctionId, Integer containerId) {
        this.junctionId = junctionId;
        this.containerId = containerId;
    }

    public Integer junction() {
        return junctionId;
    }

    Integer container() {
        return containerId;
    }
}
