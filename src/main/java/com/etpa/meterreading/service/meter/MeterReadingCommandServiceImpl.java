package com.etpa.meterreading.service.meter;

import com.etpa.meterreading.command.meter.MeterReadingCreateCommand;
import com.etpa.meterreading.command.meter.MeterReadingDeleteCommand;
import com.etpa.meterreading.command.meter.MeterReadingUpdateCommand;
import com.etpa.meterreading.dto.meter.MeterReadingListDTO;
import com.etpa.meterreading.dto.meter.MeterReadingResponse;
import com.etpa.meterreading.dto.meter.ReadingDTO;
import com.etpa.meterreading.entities.MeterReadingQueryEntity;
import com.etpa.meterreading.entities.ProfileFractionQueryEntity;
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

    private final CommandGateway commandGateway;

    private final ProfileFractionQueryService profileFractionQueryService;

    public final MeterReadingQueryService meterReadingQueryService;

    public MeterReadingCommandServiceImpl(CommandGateway commandGateway, ProfileFractionQueryService profileFractionQueryService, MeterReadingQueryService meterReadingQueryService) {
        this.commandGateway = commandGateway;
        this.profileFractionQueryService = profileFractionQueryService;
        this.meterReadingQueryService = meterReadingQueryService;
    }

    @Override
    public CompletableFuture<List<MeterReadingResponse>> createMeterReading(List<MeterReadingListDTO> meterReadingListDTO) {
        List<MeterReadingResponse> responses = new ArrayList<>();
        int size = meterReadingListDTO.stream().mapToInt(meterReadingDTOList -> meterReadingDTOList.getReadingDTOList().size()).sum();
        CompletableFuture<String>[] completableFutures = new CompletableFuture[size];
        for (MeterReadingListDTO meterReadingDTOList : meterReadingListDTO) {
            String meterId = meterReadingDTOList.getMeterId();
            String profile = meterReadingDTOList.getProfile();
            boolean isValidReading = isValidReading(Arrays.asList(meterReadingDTOList));
            if(isValidReading) {
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

                responses.add(buildResponse(meterId, profile,  true, "Meter Reading Created Successfully"));
            }
            else {
                responses.add(buildResponse(meterId, profile, false, "Meter Reading is not Valid"));
            }
        }
        return CompletableFuture.completedFuture(responses);
    }

    private MeterReadingResponse buildResponse(String meterId, String profile,  boolean status, String message) {
        if(status) {
            return MeterReadingResponse.builder()
                    .meterId(meterId)
                    .profile(profile)
                    .status(Status.SUCCESS)
                    .message(message)
                    .build();
        }
        else {
            return MeterReadingResponse.builder()
                    .meterId(meterId)
                    .profile(profile)
                    .status(Status.FAILED)
                    .message(message)
                    .build();
        }
    }

    private boolean isValidReading(List<MeterReadingListDTO> meterReadingListDTO) {
        if (profileFractionQueryService.getProfileFractionByProfile(meterReadingListDTO.get(0).getProfile()).size() == 0) {
            return false;
        }
        for (MeterReadingListDTO meterReadingDTOList : meterReadingListDTO) {
            int count = 0;
            for (ReadingDTO meterReadingDTO : meterReadingDTOList.getReadingDTOList()) {
                if (count == 0) {
                    if (meterReadingDTO.getMeterReading() < meterReadingDTOList.getReadingDTOList().get(count).getMeterReading()) {
                        return false;
                    }
                } else {
                    if (meterReadingDTO.getMeterReading() < meterReadingDTOList.getReadingDTOList().get(count - 1).getMeterReading()) {
                        return false;
                    }
                }
                count++;
            }
        }
        int totalMeterReading = getTotalMeterReading(meterReadingListDTO);
        for(MeterReadingListDTO meterReadingDTOList : meterReadingListDTO) {
            List<ProfileFractionQueryEntity> profileFractionQueryEntityList = profileFractionQueryService.getProfileFractionByProfile(meterReadingDTOList.getProfile());
            int index = 0;
            for(ReadingDTO meterReadingDTO : meterReadingDTOList.getReadingDTOList()) {
                double presentMeterReading = meterReadingDTO.getMeterReading();
                double previousMeterReading = 0.0;
                double monthlyConsumption = 0.0;
                if(index != 0) {
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
                if(monthlyConsumption > highFractionMeterReading || monthlyConsumption > lowFractionMeterReading) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getTotalMeterReading(List<MeterReadingListDTO> meterReadingListDTO) {
        int totalMeterReading = 0;
        for(MeterReadingListDTO meterReadingDTOList : meterReadingListDTO) {
            for(ReadingDTO meterReadingDTO : meterReadingDTOList.getReadingDTOList()) {
                totalMeterReading += meterReadingDTO.getMeterReading();
            }
        }
        return totalMeterReading;
    }




    @Override
    public CompletableFuture<List<MeterReadingResponse>> updateMeterReading(List<MeterReadingListDTO> meterReadingListDTO) {

        List<MeterReadingResponse> responses = new ArrayList<>();
        int size = meterReadingListDTO.stream().mapToInt(meterReadingDTOList -> meterReadingDTOList.getReadingDTOList().size()).sum();
        List<MeterReadingQueryEntity> meterReadingList = meterReadingQueryService.getMeterReadingByMeterID(meterReadingListDTO.get(0).getMeterId());
        CompletableFuture<String>[] completableFutures = new CompletableFuture[size];
        for (MeterReadingListDTO meterReadingDTOList : meterReadingListDTO) {
            String meterId = meterReadingDTOList.getMeterId();
            String profile = meterReadingDTOList.getProfile();
            boolean isValidReading = isValidReading(Arrays.asList(meterReadingDTOList));
            if(isValidReading) {
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

                responses.add(buildResponse(meterId, profile,  true, "Meter Reading Updated Successfully"));
            }
            else {
                responses.add(buildResponse(meterId, profile, false, "Meter Reading is not Valid"));
            }
        }
        return CompletableFuture.completedFuture(responses);
    }
}
