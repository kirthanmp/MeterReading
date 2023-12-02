package com.etpa.meterreading.event;

public class MeterBaseEvent <T> {
    public final T id;

    public MeterBaseEvent(T id) {
        this.id = id;
    }

}
