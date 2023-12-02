package com.etpa.meterreading.model;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class ProfileFractionAggregateModel {

    @AggregateIdentifier
    private String id;

    private String months;

    private String profile;

    private float fraction;

}
