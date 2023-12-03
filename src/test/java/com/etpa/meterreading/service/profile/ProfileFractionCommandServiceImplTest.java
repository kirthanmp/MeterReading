package com.etpa.meterreading.service.profile;

import com.etpa.meterreading.dto.profile.FractionDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionListDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionResponse;
import com.etpa.meterreading.entities.ProfileFractionQueryEntity;
import com.etpa.meterreading.utils.Status;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ProfileFractionCommandServiceImplTest {

    private static final String PROFILE_ID = "001";
    private static final String MONTHS = "JAN";
    private static final String PROFILE = "A";
    private static final float FRACTION = 1.0f;
    private final FractionDTO fractionDTO = FractionDTO.builder()
            .months(MONTHS)
            .fraction(FRACTION)
            .build();
    private final ProfileFractionQueryEntity profileFractionQueryEntity =
            ProfileFractionQueryEntity.builder()
                    .id(PROFILE_ID)
                    .months(MONTHS)
                    .profile(PROFILE)
                    .fraction(FRACTION)
                    .build();
    private final List<ProfileFractionResponse> response =
            Arrays.asList(ProfileFractionResponse.builder()
                    .profile(PROFILE)
                    .status(Status.CREATED)
                    .message("Profile Fraction Created Successfully")
                    .build());

    ProfileFractionListDTO profileFractionListDTO = ProfileFractionListDTO.builder()
            .profile(PROFILE)
            .profileFractionDTOList(Arrays.asList(FractionDTO.builder()
                    .months(MONTHS)
                    .fraction(FRACTION)
                    .build())).build();
    @Mock
    private CommandGateway commandGateway;
    @Mock
    private ProfileFractionQueryService profileFractionQueryService;
    @InjectMocks
    private ProfileFractionCommandServiceImpl profileFractionCommandService;

    @Test
    public void testCreateProfileFraction() {

        lenient().when(profileFractionQueryService
                        .getProfileFractionByProfile(any()))
                .thenReturn(Arrays.asList(profileFractionQueryEntity));

        lenient().when(commandGateway.send(any()))
                .thenReturn(CompletableFuture.completedFuture(PROFILE_ID));

        lenient().when(profileFractionQueryService.getProfileFraction(any()))
                .thenReturn(profileFractionQueryEntity);

        CompletableFuture<List<ProfileFractionResponse>> response =
                profileFractionCommandService.createProfileFraction(Arrays.asList(profileFractionListDTO));

        assertEquals(((ProfileFractionResponse) ((ArrayList) response.join()).get(0)).getProfile(), PROFILE);

        assertEquals(((ProfileFractionResponse) ((ArrayList) response.join()).get(0)).getStatus(), Status.SUCCESS);

        assertEquals(((ProfileFractionResponse) ((ArrayList) response.join()).get(0)).getMessage(), "Profile Fraction Created Successfully");
    }

    @Test
    public void testCreateProfileFractionWithInvalidFraction() {

        lenient().when(profileFractionQueryService
                        .getProfileFractionByProfile(any()))
                .thenReturn(Arrays.asList(profileFractionQueryEntity));

        lenient().when(commandGateway.send(any()))
                .thenReturn(CompletableFuture.completedFuture(PROFILE_ID));

        lenient().when(profileFractionQueryService.getProfileFraction(any()))
                .thenReturn(profileFractionQueryEntity);

        CompletableFuture<List<ProfileFractionResponse>> response =
                profileFractionCommandService.createProfileFraction(Arrays.asList(profileFractionListDTO));

        assertEquals(((ProfileFractionResponse) ((ArrayList) response.join()).get(0)).getProfile(), PROFILE);

    }

    @Test
    public void testUpdateProfileFraction() {

        lenient().when(profileFractionQueryService
                        .getProfileFractionByProfile(any()))
                .thenReturn(Arrays.asList(profileFractionQueryEntity));

        lenient().when(commandGateway.send(any()))
                .thenReturn(CompletableFuture.completedFuture(PROFILE_ID));

        lenient().when(profileFractionQueryService.getProfileFraction(any()))
                .thenReturn(profileFractionQueryEntity);

        CompletableFuture<List<ProfileFractionResponse>> response =
                profileFractionCommandService.updateProfileFraction(PROFILE, Arrays.asList(fractionDTO));

        assertEquals(((ProfileFractionResponse) ((ArrayList) response.join()).get(0)).getProfile(), PROFILE);

    }

}
