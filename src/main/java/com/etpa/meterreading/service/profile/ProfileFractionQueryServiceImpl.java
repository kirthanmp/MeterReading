package com.etpa.meterreading.service.profile;

import com.etpa.meterreading.dto.profile.ProfileFractionResponse;
import com.etpa.meterreading.entities.ProfileFractionQueryEntity;
import com.etpa.meterreading.repository.ProfileFractionRepository;
import com.etpa.meterreading.utils.Status;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileFractionQueryServiceImpl implements ProfileFractionQueryService{

    private final EventStore eventStore;

    public ProfileFractionQueryServiceImpl(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Autowired
    private ProfileFractionRepository profileFractionRepository;

    @Override
    public List<Object> listEventsForProfileFraction(String profileID) {
        return eventStore.readEvents(profileID)
                .asStream().map(s -> s.getPayload()).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public ProfileFractionQueryEntity getProfileFraction(String profileID) {
        return profileFractionRepository.findById(profileID).get();
    }

    @Override
    public List<ProfileFractionQueryEntity> getProfileFractionByProfile(String profile) {
        return profileFractionRepository.findProfileFractionQueryEntityByProfile(profile);
    }

    @Override
    public ProfileFractionResponse deleteProfile(String profile) {
        List<ProfileFractionQueryEntity> profileFractionQueryEntityList = profileFractionRepository.findProfileFractionQueryEntityByProfile(profile);
        profileFractionRepository.deleteAll(profileFractionQueryEntityList);
        return ProfileFractionResponse.builder()
                .profile(profile)
                .status(Status.SUCCESS)
                .message("Profile Deleted Successfully")
                .build();
    }
}
