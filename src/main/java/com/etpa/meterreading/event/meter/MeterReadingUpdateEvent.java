package com.etpa.meterreading.event.meter;


import com.etpa.meterreading.event.MeterBaseEvent;

public class MeterReadingUpdateEvent extends MeterBaseEvent<String> {

    public final String meterId;

    public final String profile;

    public final String months;

    public final int meterReading;

    public MeterReadingUpdateEvent(String id, String meterId, String profile, String months, int meterReading) {
        super(id);
        this.meterId = meterId;
        this.profile = profile;
        this.months = months;
        this.meterReading = meterReading;
    }

}
