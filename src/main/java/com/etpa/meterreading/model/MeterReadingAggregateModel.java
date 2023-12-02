package com.etpa.meterreading.model;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class MeterReadingAggregateModel {

    @AggregateIdentifier
    private String id;

    private String meterId;

    private String profile;

    private String months;

    private int meterReading;

}
