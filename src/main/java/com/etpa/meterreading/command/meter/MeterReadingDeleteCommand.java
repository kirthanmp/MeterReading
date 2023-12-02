package com.etpa.meterreading.command.meter;


import com.etpa.meterreading.command.BaseMeterCommand;

public class MeterReadingDeleteCommand extends BaseMeterCommand<String> {

    public MeterReadingDeleteCommand(String id) {
        super(id);
    }
}
