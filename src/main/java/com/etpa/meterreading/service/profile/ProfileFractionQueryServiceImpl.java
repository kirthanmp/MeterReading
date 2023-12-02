package com.etpa.meterreading.service.profile;

import com.etpa.meterreading.entities.ProfileFractionQueryEntity;
import com.etpa.meterreading.repository.ProfileFractionRepository;
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
    public void deleteProfileFraction(String profile) {
        profileFractionRepository.deleteProfileFraction(profile);
    }
}
