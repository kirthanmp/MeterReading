package com.etpa.meterreading.event.meter;


import com.etpa.meterreading.event.MeterBaseEvent;
import com.etpa.meterreading.utils.Status;

public class DeletedMeterReadingEvent extends MeterBaseEvent<String> {

    public final Status status;

    public DeletedMeterReadingEvent(String id, Status status) {
        super(id);
        this.status = status;
    }
}
