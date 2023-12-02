package com.etpa.meterreading.service.profile;

import com.etpa.meterreading.dto.profile.ProfileFractionDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionListDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProfileFractionCommandService {

    public CompletableFuture<List<ProfileFractionResponse>> createProfileFraction(List<ProfileFractionListDTO> profileFractionListDTO);

    public CompletableFuture<String> updateProfileFraction(String profileFractionId, ProfileFractionDTO profileFractionDTO);

    public CompletableFuture<String> deleteProfileFraction(String profileFractionId, ProfileFractionDTO profileFractionDTO);

}