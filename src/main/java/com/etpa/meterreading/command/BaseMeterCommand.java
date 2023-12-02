package com.etpa.meterreading.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class BaseMeterCommand<T> {

    @TargetAggregateIdentifier
    public final T id;

    public BaseMeterCommand(T id) {
        this.id = id;
    }
}
