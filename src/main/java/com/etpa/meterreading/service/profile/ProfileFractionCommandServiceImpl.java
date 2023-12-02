package com.etpa.meterreading.service.profile;

import com.etpa.meterreading.command.profile.ProfileFractionCreateProfileCommand;
import com.etpa.meterreading.command.profile.ProfileFractionDeleteProfileCommand;
import com.etpa.meterreading.command.profile.ProfileFractionUpdateProfileCommand;
import com.etpa.meterreading.dto.profile.FractionDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionListDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionResponse;
import com.etpa.meterreading.entities.ProfileFractionQueryEntity;
import com.etpa.meterreading.utils.Status;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProfileFractionCommandServiceImpl implements ProfileFractionCommandService {

    private final CommandGateway commandGateway;

    private final ProfileFractionQueryService profileFractionQueryService;

    public ProfileFractionCommandServiceImpl(CommandGateway commandGateway, ProfileFractionQueryService profileFractionQueryService) {
        this.commandGateway = commandGateway;
        this.profileFractionQueryService = profileFractionQueryService;
    }

    @Override
    public CompletableFuture<List<ProfileFractionResponse>> createProfileFraction(List<ProfileFractionListDTO> profileFractionListDTO) {
        List<ProfileFractionResponse> responses = new ArrayList<>();
        int size = profileFractionListDTO.stream().mapToInt(profileFractionDTOList -> profileFractionDTOList.getProfileFractionDTOList().size()).sum();
        CompletableFuture<String>[] completableFutures = new CompletableFuture[size];
        for (ProfileFractionListDTO profileFractionDTOList : profileFractionListDTO) {
            String profile = profileFractionDTOList.getProfile();
            boolean isValidFraction = isValidFraction(Arrays.asList(profileFractionDTOList));
            if (isValidFraction) {
                for (FractionDTO profileFractionDTO : profileFractionDTOList.getProfileFractionDTOList()) {
                    completableFutures[profileFractionListDTO.indexOf(profileFractionDTOList)] =
                            commandGateway.send(new ProfileFractionCreateProfileCommand(
                                    UUID.randomUUID().toString(),
                                    profileFractionDTO.getMonths(),
                                    profile,
                                    profileFractionDTO.getFraction()
                            ));
                }

                responses.add(buildResponse(profile, true));
            } else {
                responses.add(buildResponse(profile, false));
            }
        }
        return CompletableFuture.completedFuture(responses);
    }

    @Override
    public CompletableFuture<List<ProfileFractionResponse>> updateProfileFraction(String profile, List<FractionDTO> fractionDTO) {
        List<ProfileFractionResponse> responses = new ArrayList<>();
        double fraction = Double.valueOf(String.valueOf(fractionDTO.stream().mapToDouble(FractionDTO::getFraction).sum()).substring(0, 3));
        List<ProfileFractionQueryEntity> profileFractionQueryEntityList = profileFractionQueryService.getProfileFractionByProfile(profile);
        CompletableFuture<String>[] completableFuture = new CompletableFuture[fractionDTO.size()];
        if (fraction > 1.0) {
            responses.add(buildResponse(profile, false));
        } else {
            for (FractionDTO updateFraction : fractionDTO) {
                completableFuture[fractionDTO.indexOf(updateFraction)] =
                        commandGateway.send(new ProfileFractionUpdateProfileCommand(
                                profileFractionQueryEntityList.get(fractionDTO.indexOf(updateFraction)).getId(),
                                updateFraction.getMonths(),
                                profile,
                                updateFraction.getFraction()
                        ));
            }
            responses.add(buildResponse(profile, true));
        }
        return CompletableFuture.completedFuture(responses);
    }

    private boolean isValidFraction(List<ProfileFractionListDTO> profileFractionListDTO) {
        Float fraction = 0.0f;
        for (ProfileFractionListDTO profileFractionDTOList : profileFractionListDTO) {
            for (FractionDTO profileFractionDTO : profileFractionDTOList.getProfileFractionDTOList()) {
                fraction += profileFractionDTO.getFraction();
            }
        }
        Double fractionDouble = Double.valueOf(String.valueOf(fraction).substring(0, 3));
        if (fractionDouble == 1.0) {
            return true;
        } else {
            return false;
        }
    }

    private ProfileFractionResponse buildResponse(String profile, boolean status) {
        if (status) {
            return ProfileFractionResponse.builder()
                    .profile(profile)
                    .status(Status.SUCCESS)
                    .build();
        } else {
            return ProfileFractionResponse.builder()
                    .profile(profile)
                    .status(Status.FAILED)
                    .build();
        }
    }

    @Override
    public CompletableFuture<String> deleteProfileFraction(String profileFractionId, ProfileFractionDTO profileFractionDTO) {
        return commandGateway.send(new ProfileFractionDeleteProfileCommand(profileFractionId,
                profileFractionDTO.getMonths(),
                profileFractionDTO.getProfile(),
                profileFractionDTO.getFraction()));
    }

    @Override
    public void deleteProfile(String profile) {
        profileFractionQueryService.deleteProfile(profile);
    }


}
