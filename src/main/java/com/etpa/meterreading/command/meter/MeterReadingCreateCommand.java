package com.etpa.meterreading.command.meter;


import com.etpa.meterreading.command.BaseMeterCommand;

public class MeterReadingCreateCommand extends BaseMeterCommand<String> {

    public final String meterId;

    public final String profile;

    public final String months;
    public final int meterReading;

    public MeterReadingCreateCommand(String id, String meterId, String profile, String months, int meterReading) {
        super(id);
        this.meterId = meterId;
        this.profile = profile;
        this.months = months;
        this.meterReading = meterReading;
    }
}
