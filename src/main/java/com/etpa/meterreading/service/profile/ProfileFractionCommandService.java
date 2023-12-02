package com.etpa.meterreading.service.profile;

import com.etpa.meterreading.dto.profile.FractionDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionListDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProfileFractionCommandService {

    public CompletableFuture<List<ProfileFractionResponse>> createProfileFraction(List<ProfileFractionListDTO> profileFractionListDTO);

    public CompletableFuture<List<ProfileFractionResponse>> updateProfileFraction(String profileFractionId, List<FractionDTO> fractionDTO);

    public CompletableFuture<String> deleteProfileFraction(String profileFractionId, ProfileFractionDTO profileFractionDTO);

    void deleteProfile(String profile);
}
