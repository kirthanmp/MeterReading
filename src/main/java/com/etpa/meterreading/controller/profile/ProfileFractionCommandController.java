package com.etpa.meterreading.controller.profile;

import com.etpa.meterreading.dto.profile.FractionDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionListDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionResponse;
import com.etpa.meterreading.service.profile.ProfileFractionCommandService;
import com.etpa.meterreading.service.profile.ProfileFractionQueryService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/profile-fraction")
@Api(value = "ProfileFraction Commands",
        description = "ProfileFraction Commands Related Endpoints",
        tags = "ProfileFraction Commands")
public class ProfileFractionCommandController {

    private final ProfileFractionCommandService profileFractionCommandService;

    private final ProfileFractionQueryService profileFractionQueryService;

    public ProfileFractionCommandController(ProfileFractionCommandService profileFractionCommandService,
                                            ProfileFractionQueryService profileFractionQueryService) {
        this.profileFractionCommandService = profileFractionCommandService;
        this.profileFractionQueryService = profileFractionQueryService;
    }

    @PostMapping(value = "/create")
    public CompletableFuture<List<ProfileFractionResponse>> createProfileFraction(@RequestBody List<ProfileFractionListDTO> profileFractionListDTO) {
        return profileFractionCommandService.createProfileFraction(profileFractionListDTO);
    }

    @PutMapping(value = "/update/{profile}")
    public CompletableFuture<List<ProfileFractionResponse>> updateProfileFraction(@PathVariable(value = "profile") String profileFractionId,
                                                           @RequestBody List<FractionDTO> fractionDTO) {
        return profileFractionCommandService.updateProfileFraction(profileFractionId, fractionDTO);
    }

    @DeleteMapping("/profile/{profile}")
    public ProfileFractionResponse deleteProfile(@PathVariable(value = "profile") String profile) {
        return profileFractionQueryService.deleteProfile(profile);
    }
}
