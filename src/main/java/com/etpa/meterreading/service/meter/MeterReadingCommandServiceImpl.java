package com.etpa.meterreading.service.meter;

import com.etpa.meterreading.command.meter.MeterReadingCreateCommand;
import com.etpa.meterreading.command.meter.MeterReadingUpdateCommand;
import com.etpa.meterreading.dto.meter.MeterReadingListDTO;
import com.etpa.meterreading.dto.meter.MeterReadingResponse;
import com.etpa.meterreading.dto.meter.ReadingDTO;
import com.etpa.meterreading.entities.MeterReadingQueryEntity;
import com.etpa.meterreading.entities.ProfileFractionQueryEntity;
import com.etpa.meterreading.service.helper.StatusMessage;
import com.etpa.meterreading.service.profile.ProfileFractionQueryService;
import com.etpa.meterreading.utils.Status;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class MeterReadingCommandServiceImpl implements MeterReadingCommandService {

    public final MeterReadingQueryService meterReadingQueryService;
    private final CommandGateway commandGateway;
    private final ProfileFractionQueryService profileFractionQueryService;

    public MeterReadingCommandServiceImpl(CommandGateway commandGateway, ProfileFractionQueryService profileFractionQueryService, MeterReadingQueryService meterReadingQueryService) {
        this.commandGateway = commandGateway;
        this.profileFractionQueryService = profileFractionQueryService;
        this.meterReadingQueryService = meterReadingQueryService;
    }

    @Override
    public CompletableFuture<List<MeterReadingResponse>> createMeterReading(List<MeterReadingListDTO> meterReadingListDTO) {
        List<MeterReadingResponse> responses = new ArrayList<>();
        String message = "Meter reading created successfully";
        int size = meterReadingListDTO.stream().mapToInt(meterReadingDTOList -> meterReadingDTOList.getReadingDTOList().size()).sum();
        CompletableFuture<String>[] completableFutures = new CompletableFuture[size];
        for (MeterReadingListDTO meterReadingDTOList : meterReadingListDTO) {
            String meterId = meterReadingDTOList.getMeterId();
            String profile = meterReadingDTOList.getProfile();
            /*Check if meter reading is valid*/
            StatusMessage status = isValidReading(Arrays.asList(meterReadingDTOList), message);
            if (status.isValid()) {
                for (ReadingDTO meterReadingDTO : meterReadingDTOList.getReadingDTOList()) {
                    completableFutures[meterReadingListDTO.indexOf(meterReadingDTOList)] =
                            commandGateway.send(new MeterReadingCreateCommand(
                                    UUID.randomUUID().toString(),
                                    meterId,
                                    profile,
                                    meterReadingDTO.getMonths(),
                                    meterReadingDTO.getMeterReading()
                            ));
                }

                responses.add(buildResponse(meterId, profile, status.isValid(), status.getMessage()));
            } else {
                responses.add(buildResponse(meterId, profile, status.isValid(), status.getMessage()));
            }
        }
        return CompletableFuture.completedFuture(responses);
    }

    private MeterReadingResponse buildResponse(String meterId, String profile, boolean status, String message) {
        if (status) {
            return MeterReadingResponse.builder()
                    .meterId(meterId)
                    .profile(profile)
                    .status(Status.SUCCESS)
                    .message(message)
                    .build();
        } else {
            return MeterReadingResponse.builder()
                    .meterId(meterId)
                    .profile(profile)
                    .status(Status.FAILED)
                    .message(message)
                    .build();
        }
    }

    private StatusMessage isValidReading(List<MeterReadingListDTO> meterReadingListDTO, String message) {
        /* Check if profile fraction exists */
        if (profileFractionQueryService.getProfileFractionByProfile(meterReadingListDTO.get(0).getProfile()).size() == 0) {
            message = "Profile Fraction Does not Exist";
            return buildResponse(false, message);
        }
        /* Check if the consumptions are correct, it should always be in increasing order, cannot have less than previous month.*/
        for (MeterReadingListDTO meterReadingDTOList : meterReadingListDTO) {
            int count = 0;
            for (ReadingDTO meterReadingDTO : meterReadingDTOList.getReadingDTOList()) {
                if (count == 0) {
                    if (meterReadingDTO.getMeterReading() < meterReadingDTOList.getReadingDTOList().get(count).getMeterReading()) {
                        message = "Meter Reading Should be in Increasing Order";
                        return buildResponse(false, message);
                    }
                } else {
                    if (meterReadingDTO.getMeterReading() < meterReadingDTOList.getReadingDTOList().get(count - 1).getMeterReading()) {
                        message = "Meter Reading Should be in Increasing Order";
                        return buildResponse(false, message);
                    }
                }
                count++;
            }
        }
        /* Monthly consumption should have consistency with fraction, with an allowed tolerance of 25% */
        int totalMeterReading = getTotalMeterReading(meterReadingListDTO);
        for (MeterReadingListDTO meterReadingDTOList : meterReadingListDTO) {
            List<ProfileFractionQueryEntity> profileFractionQueryEntityList = profileFractionQueryService.getProfileFractionByProfile(meterReadingDTOList.getProfile());
            int index = 0;
            for (ReadingDTO meterReadingDTO : meterReadingDTOList.getReadingDTOList()) {
                double presentMeterReading = meterReadingDTO.getMeterReading();
                double previousMeterReading = 0.0;
                double monthlyConsumption = 0.0;
                if (index != 0) {
                    previousMeterReading = meterReadingDTOList.getReadingDTOList().get(index - 1).getMeterReading();
                    monthlyConsumption = presentMeterReading - previousMeterReading;
                } else {
                    monthlyConsumption = presentMeterReading;
                }
                index++;
                float fraction = profileFractionQueryEntityList.get(0).getFraction();
                double fractionMeterReading = (int) (totalMeterReading * fraction);
                double highFractionMeterReading = (int) (fractionMeterReading * 1.25);
                double lowFractionMeterReading = (int) (fractionMeterReading * 0.75);
                /*check fractionMeterReading is within highFractionMeterReading and lowFractionMeterReading*/
                if (monthlyConsumption > highFractionMeterReading || monthlyConsumption > lowFractionMeterReading) {
                    message = "Monthly Consumption Should be within 25% of Profile Fraction, abnormal consumption";
                    return buildResponse(false, message);
                }
            }
        }
        return buildResponse(true, message);
    }

    private int getTotalMeterReading(List<MeterReadingListDTO> meterReadingListDTO) {
        int totalMeterReading = 0;
        for (MeterReadingListDTO meterReadingDTOList : meterReadingListDTO) {
            for (ReadingDTO meterReadingDTO : meterReadingDTOList.getReadingDTOList()) {
                totalMeterReading += meterReadingDTO.getMeterReading();
            }
        }
        return totalMeterReading;
    }

    @Override
    public CompletableFuture<List<MeterReadingResponse>> updateMeterReading(List<MeterReadingListDTO> meterReadingListDTO) {
        List<MeterReadingResponse> responses = new ArrayList<>();
        String message = "Meter reading updated successfully";
        int size = meterReadingListDTO.stream().mapToInt(meterReadingDTOList -> meterReadingDTOList.getReadingDTOList().size()).sum();
        List<MeterReadingQueryEntity> meterReadingList = meterReadingQueryService.getMeterReadingByMeterID(meterReadingListDTO.get(0).getMeterId());
        CompletableFuture<String>[] completableFutures = new CompletableFuture[size];
        for (MeterReadingListDTO meterReadingDTOList : meterReadingListDTO) {
            String meterId = meterReadingDTOList.getMeterId();
            String profile = meterReadingDTOList.getProfile();
            StatusMessage status = isValidReading(Arrays.asList(meterReadingDTOList), message);
            if (status.isValid()) {
                for (ReadingDTO meterReadingDTO : meterReadingDTOList.getReadingDTOList()) {
                    completableFutures[meterReadingListDTO.indexOf(meterReadingDTOList)] =
                            commandGateway.send(new MeterReadingUpdateCommand(
                                    meterReadingList.get(meterReadingDTOList.getReadingDTOList().indexOf(meterReadingDTO)).getId(),
                                    meterId,
                                    profile,
                                    meterReadingDTO.getMonths(),
                                    meterReadingDTO.getMeterReading()
                            ));
                }

                responses.add(buildResponse(meterId, profile, status.isValid(), status.getMessage()));
            } else {
                responses.add(buildResponse(meterId, profile, status.isValid(), status.getMessage()));
            }
        }
        return CompletableFuture.completedFuture(responses);
    }

    private StatusMessage buildResponse(boolean isValid, String message) {
        return StatusMessage.builder()
                .isValid(isValid)
                .message(message)
                .build();
    }

    @Override
    public MeterReadingResponse deleteMeterReading(String meterReadingId) {
        return null;
    }
}
