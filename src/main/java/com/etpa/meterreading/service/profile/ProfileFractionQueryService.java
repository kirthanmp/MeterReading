package com.etpa.meterreading.service.profile;

import com.etpa.meterreading.dto.profile.ProfileFractionResponse;
import com.etpa.meterreading.entities.ProfileFractionQueryEntity;

import java.util.List;

public interface ProfileFractionQueryService {

    List<Object> listEventsForProfileFraction(String profileID);

    ProfileFractionQueryEntity getProfileFraction(String profileID);

    List<ProfileFractionQueryEntity> getProfileFractionByProfile(String profile);

    ProfileFractionResponse deleteProfile(String profile);

}
