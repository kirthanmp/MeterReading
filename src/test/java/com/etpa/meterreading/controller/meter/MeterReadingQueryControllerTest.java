package com.etpa.meterreading.controller.meter;

import com.etpa.meterreading.entities.MeterReadingQueryEntity;
import com.etpa.meterreading.service.meter.MeterReadingQueryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeterReadingQueryControllerTest {

    @Mock
    private MeterReadingQueryService meterReadingQueryService;

    @InjectMocks
    private MeterReadingQueryController meterReadingQueryController;

    private static final String METER_ID = "001";
    private static final String PROFILE = "A";
    private static final String MONTHS = "JAN";
    private static final int METER_READING = 100;

    @Test
    public void testGetMeterReadingById() {
        when(meterReadingQueryService.getMeterReading(METER_ID))
                .thenReturn(meterReadingQueryEntity);
        meterReadingQueryController.getMeterReadingById(METER_ID);
        verify(meterReadingQueryService).getMeterReading(METER_ID);
    }

    @Test
    public void testGetMeterReadingByProfile() {
        when(meterReadingQueryService.getMeterReadingByProfile(PROFILE))
                .thenReturn(List.of(meterReadingQueryEntity));
        meterReadingQueryController.getMeterReadingByProfile(PROFILE);
        verify(meterReadingQueryService).getMeterReadingByProfile(PROFILE);
    }

    @Test
    public void testListEventsForMeterReading() {
        when(meterReadingQueryService.listEventsForMeterReading(METER_ID))
                .thenReturn(List.of(meterReadingQueryEntity));
        meterReadingQueryController.listEventsForMeterReading(METER_ID);
        verify(meterReadingQueryService).listEventsForMeterReading(METER_ID);
    }


    private final MeterReadingQueryEntity meterReadingQueryEntity =
            MeterReadingQueryEntity.builder()
                    .meterID(METER_ID)
                    .profile(PROFILE)
                    .months(MONTHS)
                    .meterReading(METER_READING)
                    .build();


}
