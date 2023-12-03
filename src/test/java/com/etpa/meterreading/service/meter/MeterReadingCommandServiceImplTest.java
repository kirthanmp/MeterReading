package com.etpa.meterreading.service.meter;

import com.etpa.meterreading.dto.meter.MeterReadingListDTO;
import com.etpa.meterreading.dto.meter.MeterReadingResponse;
import com.etpa.meterreading.dto.meter.ReadingDTO;
import com.etpa.meterreading.entities.MeterReadingQueryEntity;
import com.etpa.meterreading.entities.ProfileFractionQueryEntity;
import com.etpa.meterreading.service.profile.ProfileFractionQueryService;
import com.etpa.meterreading.utils.Status;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MeterReadingCommandServiceImplTest {

    @Mock
    private CommandGateway commandGateway;

    @Mock
    private MeterReadingQueryService meterReadingQueryService;

    @Mock
    private ProfileFractionQueryService profileFractionQueryService;

    @InjectMocks
    private MeterReadingCommandServiceImpl meterReadingCommandService;

    private static final String METER_READING_ID = "1";
    private static final String METER_ID = "001";
    private static final String PROFILE = "A";
    private static final String MONTHS = "JAN";
    private static final int METER_READING = 100;

    private static final String PROFILE_ID = "1";
    private static final float FRACTION = 0.5f;

    @Test
    public void testCreateMeterReading() {

        lenient().when(meterReadingQueryService.getMeterReadingByProfile(PROFILE)).thenReturn(List.of(meterReadingQueryEntity));

        lenient().when(commandGateway.send(any())).thenReturn(CompletableFuture.completedFuture(PROFILE));

        lenient().when(meterReadingQueryService.getMeterReadingByMeterID(METER_ID)).thenReturn(List.of(meterReadingQueryEntity));

        when(profileFractionQueryService.getProfileFractionByProfile(PROFILE)).thenReturn(List.of(profileFractionQueryEntity));
        CompletableFuture<List<MeterReadingResponse>> meterReadingResponse =
                meterReadingCommandService.createMeterReading(List.of(meterReadingListDTO));

        assertEquals(meterReadingResponse.join().get(0).getMeterId(), METER_ID);
        assertEquals(meterReadingResponse.join().get(0).getProfile(), PROFILE);

    }

    @Test
    public void testCreateMeterReadingWithInvalidReading() {

        lenient().when(meterReadingQueryService.getMeterReadingByProfile(PROFILE)).thenReturn(List.of(meterReadingQueryEntity));

        lenient().when(commandGateway.send(any())).thenReturn(CompletableFuture.completedFuture(PROFILE));

        lenient().when(meterReadingQueryService.getMeterReadingByMeterID(METER_ID)).thenReturn(List.of(meterReadingQueryEntity));

        when(profileFractionQueryService.getProfileFractionByProfile(PROFILE)).thenReturn(List.of(profileFractionQueryEntity));
        CompletableFuture<List<MeterReadingResponse>> meterReadingResponse =
                meterReadingCommandService.createMeterReading(List.of(meterReadingListDTO));

        assertEquals(meterReadingResponse.join().get(0).getMeterId(), METER_ID);
        assertEquals(meterReadingResponse.join().get(0).getProfile(), PROFILE);
        assertEquals(meterReadingResponse.join().get(0).getStatus(), Status.FAILED);

    }

    @Test
    public void testUpdateMeterReading() {

        lenient().when(meterReadingQueryService.getMeterReadingByProfile(PROFILE)).thenReturn(List.of(meterReadingQueryEntity));

        lenient().when(commandGateway.send(any())).thenReturn(CompletableFuture.completedFuture(PROFILE));

        lenient().when(meterReadingQueryService.getMeterReadingByMeterID(METER_ID)).thenReturn(List.of(meterReadingQueryEntity));

        when(profileFractionQueryService.getProfileFractionByProfile(PROFILE)).thenReturn(List.of(profileFractionQueryEntity));
        CompletableFuture<List<MeterReadingResponse>> meterReadingResponse =
                meterReadingCommandService.updateMeterReading(List.of(meterReadingListDTO));

        assertEquals(meterReadingResponse.join().get(0).getMeterId(), METER_ID);
        assertEquals(meterReadingResponse.join().get(0).getProfile(), PROFILE);

    }

    @Test
    public void testUpdateMeterReadingWithInvalidReading() {

        lenient().when(meterReadingQueryService.getMeterReadingByProfile(PROFILE)).thenReturn(List.of(meterReadingQueryEntity));

        lenient().when(commandGateway.send(any())).thenReturn(CompletableFuture.completedFuture(PROFILE));

        lenient().when(meterReadingQueryService.getMeterReadingByMeterID(METER_ID)).thenReturn(List.of(meterReadingQueryEntity));

        when(profileFractionQueryService.getProfileFractionByProfile(PROFILE)).thenReturn(List.of(profileFractionQueryEntity));
        CompletableFuture<List<MeterReadingResponse>> meterReadingResponse =
                meterReadingCommandService.updateMeterReading(List.of(meterReadingListDTO));

        assertEquals(meterReadingResponse.join().get(0).getMeterId(), METER_ID);
        assertEquals(meterReadingResponse.join().get(0).getProfile(), PROFILE);
        assertEquals(meterReadingResponse.join().get(0).getStatus(), Status.FAILED);

    }

    private final MeterReadingQueryEntity meterReadingQueryEntity = MeterReadingQueryEntity.builder()
            .id(METER_READING_ID)
            .meterID(METER_ID)
            .profile(PROFILE)
            .months(MONTHS)
            .meterReading(METER_READING)
            .build();

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

    private final ProfileFractionQueryEntity profileFractionQueryEntity = ProfileFractionQueryEntity.builder()
            .id(PROFILE_ID)
            .profile(PROFILE)
            .fraction(FRACTION)
            .build();
}
