package com.etpa.meterreading.controller.meter;

import com.etpa.meterreading.dto.meter.MeterReadingListDTO;
import com.etpa.meterreading.dto.meter.MeterReadingResponse;
import com.etpa.meterreading.dto.meter.ReadingDTO;
import com.etpa.meterreading.entities.MeterReadingQueryEntity;
import com.etpa.meterreading.service.meter.MeterReadingCommandService;
import com.etpa.meterreading.service.meter.MeterReadingQueryService;
import com.etpa.meterreading.utils.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeterReadingCommandControllerTest {

    private static final String METER_ID = "001";
    private static final String PROFILE = "A";
    private static final String MONTHS = "JAN";
    private static final int METER_READING = 100;

    @Mock
    private MeterReadingCommandService meterReadingCommandService;
    @Mock
    private MeterReadingQueryService meterReadingQueryService;
    @InjectMocks
    private MeterReadingCommandController meterReadingCommandController;

    @Test
    public void testCreateMeterReading() {
        when(meterReadingCommandService.createMeterReading(List.of(meterReadingListDTO)))
                .thenReturn(CompletableFuture.completedFuture(List.of(meterReadingResponse)));
        meterReadingCommandController.createMeterReading(List.of(meterReadingListDTO));
        verify(meterReadingCommandService).createMeterReading(List.of(meterReadingListDTO));
    }

    @Test
    public void testUpdateMeterReading() {
        when(meterReadingCommandService.updateMeterReading(List.of(meterReadingListDTO)))
                .thenReturn(CompletableFuture.completedFuture(List.of(meterReadingResponse)));
        meterReadingCommandController.updateMeterReading(List.of(meterReadingListDTO));
        verify(meterReadingCommandService).updateMeterReading(List.of(meterReadingListDTO));
    }

    @Test
    public void testDeleteMeterReading() {
        when(meterReadingQueryService.deleteMeterReading(METER_ID))
                .thenReturn(meterReadingResponse);
        meterReadingCommandController.deleteMeterReading(METER_ID);
        verify(meterReadingQueryService).deleteMeterReading(METER_ID);
    }

    @Test
    public void testGetMeterReadingByMeterIdAndMonths() {
        when(meterReadingQueryService.getMeterReadingByMeterIDAndMonth(METER_ID, MONTHS))
                .thenReturn(meterReadingQueryEntity);
        meterReadingCommandController.getMeterReadingByMeterIdAndMonths(METER_ID, MONTHS);
        verify(meterReadingQueryService).getMeterReadingByMeterIDAndMonth(METER_ID, MONTHS);
    }

    private final MeterReadingListDTO meterReadingListDTO = MeterReadingListDTO.builder()
            .meterId(METER_ID)
            .profile(PROFILE)
            .readingDTOList(List.of(ReadingDTO.builder()
                    .months(MONTHS)
                    .meterReading(METER_READING)
                    .build()))
            .build();
    private final MeterReadingResponse meterReadingResponse = MeterReadingResponse.builder()
            .meterId(METER_ID)
            .profile(PROFILE)
            .status(Status.SUCCESS)
            .message("Meter Reading Created Successfully")
            .build();
    private final MeterReadingQueryEntity meterReadingQueryEntity = MeterReadingQueryEntity.builder()
            .id(METER_ID)
            .meterID(METER_ID)
            .profile(PROFILE)
            .months(MONTHS)
            .meterReading(METER_READING)
            .build();

}
