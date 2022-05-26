package ro.pub.cs.systems.eim.practicaltest02.model;

import java.time.Instant;

public class Capsule {
    private String value;
    private String received_time;

    public Capsule(String value, String received_time) {
        this.value = value;
        this.received_time = received_time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReceived_time() {
        return received_time;
    }

    public void setReceived_time(String received_time) {
        this.received_time = received_time;
    }
}
