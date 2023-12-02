package com.etpa.meterreading.event;

public class ProfileBaseEvent<T> {
    public final T id;

    public ProfileBaseEvent(T id) {
        this.id = id;
    }

}
