package com.etpa.meterreading.service.meter;

import com.etpa.meterreading.dto.meter.MeterReadingDTO;
import com.etpa.meterreading.dto.meter.MeterReadingListDTO;
import com.etpa.meterreading.dto.meter.MeterReadingResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MeterReadingCommandService {

    public CompletableFuture<List<MeterReadingResponse>> createMeterReading(List<MeterReadingListDTO> meterReadingListDTO);

    public CompletableFuture<String> updateMeterReading(String meterReadingId, MeterReadingDTO meterReadingDTO);

    public CompletableFuture<String> deleteMeterReading(String meterReadingId);

}
