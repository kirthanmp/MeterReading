package com.etpa.meterreading.event.meter;


import com.etpa.meterreading.event.MeterBaseEvent;

public class MeterReadingDeleteEvent extends MeterBaseEvent<String> {
    public MeterReadingDeleteEvent(String id) {
        super(id);

    }
}
