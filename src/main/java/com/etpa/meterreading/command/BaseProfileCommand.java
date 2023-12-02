package com.etpa.meterreading.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class BaseProfileCommand<T> {

    @TargetAggregateIdentifier
    public final T id;

    public BaseProfileCommand(T id) {
        this.id = id;
    }

}
