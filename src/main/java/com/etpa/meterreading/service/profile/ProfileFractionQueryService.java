package com.etpa.meterreading.service.profile;

import com.etpa.meterreading.entities.ProfileFractionQueryEntity;

import java.util.List;

public interface ProfileFractionQueryService {

    public List<Object> listEventsForProfileFraction(String profileID);

    ProfileFractionQueryEntity getProfileFraction(String profileID);

    List<ProfileFractionQueryEntity> getProfileFractionByProfile(String profile);

    void deleteProfileFraction(String profile);
}
