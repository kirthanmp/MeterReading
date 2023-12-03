package com.etpa.meterreading.controller.profile;

import com.etpa.meterreading.entities.ProfileFractionQueryEntity;
import com.etpa.meterreading.service.profile.ProfileFractionQueryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileFractionQueryControllerTest {

    @Mock
    private ProfileFractionQueryService profileFractionQueryService;

    @InjectMocks
    private ProfileFractionQueryController profileFractionQueryController;

    private static final String PROFILE_ID = "001";
    private static final String PROFILE = "A";
    private static final float FRACTION = 0.5f;
    private static final String MONTHS = "JAN";

    @Test
    public void testGetProfileFractionById() {
        when(profileFractionQueryService.getProfileFraction(PROFILE_ID))
                .thenReturn(profileFractionQueryEntity);
        assertEquals(profileFractionQueryService.getProfileFraction(PROFILE_ID).getId(), PROFILE_ID);
    }

    @Test
    public void testGetProfileFractionByProfile() {
        when(profileFractionQueryService.getProfileFractionByProfile(PROFILE))
                .thenReturn(List.of(profileFractionQueryEntity));
        assertEquals(profileFractionQueryService.getProfileFractionByProfile(PROFILE), List.of(profileFractionQueryEntity));
    }

    @Test
    public void testListEventsForProfileFraction() {
        when(profileFractionQueryService.listEventsForProfileFraction(PROFILE_ID))
                .thenReturn(List.of(profileFractionQueryEntity));
        assertEquals(profileFractionQueryService.listEventsForProfileFraction(PROFILE_ID), List.of(profileFractionQueryEntity));
    }


    private static final ProfileFractionQueryEntity profileFractionQueryEntity =
            ProfileFractionQueryEntity.builder()
                    .id(PROFILE_ID)
                    .profile(PROFILE)
                    .fraction(FRACTION)
                    .months(MONTHS)
                    .build();
}
