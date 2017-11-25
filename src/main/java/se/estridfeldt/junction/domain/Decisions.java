package se.estridfeldt.junction.domain;

public class Decisions {
    public static String whereShouldContainerGo(Integer junctionId, Integer containerId) {
        try {
            Thread.sleep(5); // just to simulate resource hunger
            return "CVR_" + junctionId + "_" + (int) (Math.random() * 10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
