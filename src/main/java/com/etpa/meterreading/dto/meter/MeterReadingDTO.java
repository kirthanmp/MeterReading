package com.etpa.meterreading.dto.meter;

import lombok.Data;

@Data
public class MeterReadingDTO {

    private String meterId;

    private String profile;

    private String months;

    private int meterReading;

}
