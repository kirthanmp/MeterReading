package com.etpa.meterreading.controller.profile;

import com.etpa.meterreading.dto.profile.FractionDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionListDTO;
import com.etpa.meterreading.service.profile.ProfileFractionCommandService;
import com.etpa.meterreading.service.profile.ProfileFractionQueryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProfileFractionCommandControllerTest {

    @Mock
    private ProfileFractionCommandService profileFractionCommandService;

    @Mock
    private ProfileFractionQueryService profileFractionQueryService;

    @InjectMocks
    private ProfileFractionCommandController profileFractionCommandController;

    private static final String MONTHS = "JAN";
    private static final String PROFILE = "A";
    private static final float FRACTION = 1.0f;

    private static final String PROFILE_FRACTION_ID = "1";

    @Test
    public void testCreateProfileFraction() {
        profileFractionCommandController.createProfileFraction(List.of(profileFractionListDTO));
        verify(profileFractionCommandService).createProfileFraction(List.of(profileFractionListDTO));
    }

    @Test
    public void testUpdateProfileFraction() {
        profileFractionCommandController.updateProfileFraction(PROFILE_FRACTION_ID, List.of(fractionDTO));
        verify(profileFractionCommandService).updateProfileFraction(PROFILE_FRACTION_ID, List.of(fractionDTO));
    }

    @Test
    public void testDeleteProfileFraction() {
        profileFractionCommandController.deleteProfile(PROFILE);
        verify(profileFractionQueryService).deleteProfile(PROFILE);
    }

    private final ProfileFractionListDTO profileFractionListDTO = ProfileFractionListDTO.builder()
            .profile(PROFILE)
            .profileFractionDTOList(Arrays.asList(FractionDTO.builder()
                    .months(MONTHS)
                    .fraction(FRACTION)
                    .build())).build();

    private final FractionDTO fractionDTO = FractionDTO.builder()
            .months(MONTHS)
            .fraction(FRACTION)
            .build();



}
