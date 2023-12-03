package com.etpa.meterreading.service.meter;

import com.etpa.meterreading.dto.meter.MeterReadingResponse;
import com.etpa.meterreading.entities.MeterReadingQueryEntity;

import java.util.List;

public interface MeterReadingQueryService {

    List<Object> listEventsForMeterReading(String meterID);

    MeterReadingQueryEntity getMeterReading(String meterID);

    List<MeterReadingQueryEntity> getMeterReadingByProfile(String profile);

    List<MeterReadingQueryEntity> getMeterReadingByMeterID(String meterID);

    MeterReadingResponse deleteMeterReading(String meterID);

    MeterReadingQueryEntity getMeterReadingByMeterIDAndMonth(String meterID, String month);

}
