package com.etpa.meterreading.service.profile;

import com.etpa.meterreading.entities.ProfileFractionQueryEntity;
import com.etpa.meterreading.repository.ProfileFractionRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileFractionQueryServiceImplTest {

    @Mock
    private EventStore eventStore;

    @Mock
    private ProfileFractionRepository profileFractionRepository;

    @InjectMocks
    private ProfileFractionQueryServiceImpl profileFractionQueryService;

    private static final String PROFILE_ID = "001";
    private static final String PROFILE = "A";
    private static final float FRACTION = 0.5f;
    private static final String MONTHS = "JAN";

    @Test
    public void getProfileFractionTest() {
        when(profileFractionRepository.findById(PROFILE_ID)).thenReturn(Optional.ofNullable(profileFractionQueryEntity));
        Optional<ProfileFractionQueryEntity> response = profileFractionRepository.findById(PROFILE_ID);
        assertEquals(response.get().getId(), PROFILE_ID);
    }

    @Test
    public void getProfileFractionByProfileTest() {
        when(profileFractionRepository.findProfileFractionQueryEntityByProfile(PROFILE)).thenReturn(List.of(profileFractionQueryEntity));
        assertEquals(profileFractionRepository.findProfileFractionQueryEntityByProfile(PROFILE).get(0).getProfile(), PROFILE);
    }

    @Test
    public void deleteProfileTest() {
        when(profileFractionRepository.findProfileFractionQueryEntityByProfile(PROFILE)).thenReturn(List.of(profileFractionQueryEntity));
        assertEquals(profileFractionRepository.findProfileFractionQueryEntityByProfile(PROFILE).get(0).getProfile(), PROFILE);
    }

    private static final ProfileFractionQueryEntity profileFractionQueryEntity =
            ProfileFractionQueryEntity.builder()
                    .id(PROFILE_ID)
                    .profile(PROFILE)
                    .fraction(FRACTION)
                    .months(MONTHS)
                    .build();

}
