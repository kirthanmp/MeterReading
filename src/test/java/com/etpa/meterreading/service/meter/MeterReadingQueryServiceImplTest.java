package com.etpa.meterreading.service.meter;

import com.etpa.meterreading.entities.MeterReadingQueryEntity;
import com.etpa.meterreading.repository.MeterReadingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeterReadingQueryServiceImplTest {

    private static final String METER_ID = "001";
    private static final String PROFILE = "A";
    private static final String MONTHS = "JAN";
    private static final int METER_READING = 100;
    private final MeterReadingQueryEntity meterReadingQueryEntity =
            MeterReadingQueryEntity.builder()
                    .meterID(METER_ID)
                    .profile(PROFILE)
                    .months(MONTHS)
                    .meterReading(METER_READING)
                    .build();
    @Mock
    private EventStore eventStore;
    @Mock
    private MeterReadingRepository meterReadingRepository;
    @InjectMocks
    private MeterReadingQueryServiceImpl meterReadingQueryService;

    @Test
    public void getMeterReadingTest() {
        when(meterReadingRepository.findById(METER_ID)).thenReturn(Optional.ofNullable(meterReadingQueryEntity));
        Optional<MeterReadingQueryEntity> response = meterReadingRepository.findById(METER_ID);
        assertEquals(response.get().getMeterID(), METER_ID);
    }

    @Test
    public void getMeterReadingByProfileTest() {
        when(meterReadingRepository.findMeterReadingQueryEntityByProfile(PROFILE)).thenReturn(List.of(meterReadingQueryEntity));
        assertEquals(meterReadingRepository.findMeterReadingQueryEntityByProfile(PROFILE).get(0).getProfile(), PROFILE);
    }

    @Test
    public void getMeterReadingByMeterIDTest() {
        when(meterReadingRepository.findMeterReadingQueryEntityByMeterID(METER_ID)).thenReturn(List.of(meterReadingQueryEntity));
        assertEquals(meterReadingRepository.findMeterReadingQueryEntityByMeterID(METER_ID).get(0).getMeterID(), METER_ID);
    }

    @Test
    public void deleteMeterReadingTest() {
        when(meterReadingRepository.findMeterReadingQueryEntityByMeterID(METER_ID)).thenReturn(List.of(meterReadingQueryEntity));
        meterReadingRepository.deleteAll(List.of(meterReadingQueryEntity));
        assertEquals(meterReadingRepository.findMeterReadingQueryEntityByMeterID(METER_ID).get(0).getMeterID(), METER_ID);
    }

    @Test
    public void getMeterReadingByMeterIDAndMonthTest() {
        when(meterReadingRepository.findMeterReadingQueryEntityByMeterIDAndMonths(METER_ID, MONTHS)).thenReturn(meterReadingQueryEntity);
        assertEquals(meterReadingRepository.findMeterReadingQueryEntityByMeterIDAndMonths(METER_ID, MONTHS).getMeterID(), METER_ID);
    }
}
