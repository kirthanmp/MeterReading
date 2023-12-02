package com.etpa.meterreading.service.meter;

import com.etpa.meterreading.entities.MeterReadingQueryEntity;

import java.util.List;

public interface MeterReadingQueryService {

    public List<Object> listEventsForMeterReading(String meterID);

    MeterReadingQueryEntity getMeterReading(String meterID);

    List<MeterReadingQueryEntity> getMeterReadingByProfile(String profile);

}
