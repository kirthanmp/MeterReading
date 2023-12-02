package com.etpa.meterreading.controller.profile;

import com.etpa.meterreading.dto.profile.ProfileFractionDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionListDTO;
import com.etpa.meterreading.dto.profile.ProfileFractionResponse;
import com.etpa.meterreading.service.profile.ProfileFractionCommandService;
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

    public ProfileFractionCommandController(ProfileFractionCommandService profileFractionCommandService) {
        this.profileFractionCommandService = profileFractionCommandService;
    }

    @PostMapping(value = "/create")
    public CompletableFuture<List<ProfileFractionResponse>> createProfileFraction(@RequestBody List<ProfileFractionListDTO> profileFractionListDTO) {
        return profileFractionCommandService.createProfileFraction(profileFractionListDTO);
    }

    @PutMapping(value = "/update/{profileFractionId}")
    public CompletableFuture<String> updateProfileFraction(@PathVariable(value = "profileFractionId") String profileFractionId,
                                                           @RequestBody ProfileFractionDTO profileFractionDTO) {
        return profileFractionCommandService.updateProfileFraction(profileFractionId, profileFractionDTO);
    }

    @DeleteMapping(value = "/delete/{profileFractionId}")
    public CompletableFuture<String> deleteProfileFraction(@PathVariable(value = "profileFractionId") String profileFractionId,
                                                           @RequestBody ProfileFractionDTO profileFractionDTO) {
        return profileFractionCommandService.deleteProfileFraction(profileFractionId, profileFractionDTO);
    }

}
