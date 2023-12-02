package com.etpa.meterreading.aggregate.profile;

import com.etpa.meterreading.entities.ProfileFractionQueryEntity;
import com.etpa.meterreading.event.ProfileBaseEvent;
import com.etpa.meterreading.repository.ProfileFractionRepository;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ProfileFractionQueryManager {

    @Autowired
    private ProfileFractionRepository profileFractionRepository;

    @Autowired
    @Qualifier("profileFractionAggregateRepository")
    private EventSourcingRepository<ProfileFractionAggregate> profileFractionAggregateRepository;

    @EventSourcingHandler
    void on(ProfileBaseEvent event) {
        persistProfileFraction(buildQueryProfileFraction(getProfileFractionFromEvent(event)));
    }

    private ProfileFractionQueryEntity buildQueryProfileFraction(ProfileFractionAggregate profileFractionAggregate) {
        ProfileFractionQueryEntity profileFractionQueryEntity = findExistingOrCreateQueryProfileFraction(profileFractionAggregate.getId());

        profileFractionQueryEntity.setId(profileFractionAggregate.getId());
        profileFractionQueryEntity.setMonths(profileFractionAggregate.getMonths());
        profileFractionQueryEntity.setProfile(profileFractionAggregate.getProfile());
        profileFractionQueryEntity.setFraction(profileFractionAggregate.getFraction());

        return profileFractionQueryEntity;
    }

    private ProfileFractionAggregate getProfileFractionFromEvent(ProfileBaseEvent event) {
        return profileFractionAggregateRepository.load(event.id.toString()).getWrappedAggregate().getAggregateRoot();
    }

    private ProfileFractionQueryEntity findExistingOrCreateQueryProfileFraction(String id) {
        return profileFractionRepository.findById(id).isPresent() ? profileFractionRepository.findById(id).get() :
                new ProfileFractionQueryEntity();
    }

    private void persistProfileFraction(ProfileFractionQueryEntity profileFractionQueryEntity) {
        profileFractionRepository.save(profileFractionQueryEntity);
    }

}
