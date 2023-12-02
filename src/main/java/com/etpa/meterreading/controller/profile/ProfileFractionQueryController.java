package com.etpa.meterreading.controller.profile;

import com.etpa.meterreading.entities.ProfileFractionQueryEntity;
import com.etpa.meterreading.service.profile.ProfileFractionQueryService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile-fraction")
@Api(value = "ProfileFraction Queries",
        description = "ProfileFraction Queries Related Endpoints",
        tags = "ProfileFraction Queries")
public class ProfileFractionQueryController {

    private final ProfileFractionQueryService profileFractionQueryService;

    public ProfileFractionQueryController(ProfileFractionQueryService profileFractionQueryService) {
        this.profileFractionQueryService = profileFractionQueryService;
    }

    @GetMapping("{profileFractionId}")
    public ProfileFractionQueryEntity getProfileFractionById(@PathVariable(value = "profileFractionId") String profileFractionId) {
        return profileFractionQueryService.getProfileFraction(profileFractionId);
    }

    @GetMapping("/{profileFractionId}/events")
    public List<Object> listEventsForProfileFraction(@PathVariable(value = "profileFractionId") String profileFractionId) {
        return profileFractionQueryService.listEventsForProfileFraction(profileFractionId);
    }

    @GetMapping("/profile/{profile}")
    public List<ProfileFractionQueryEntity> getProfileFractionByProfile(@PathVariable(value = "profile") String profile) {
        return profileFractionQueryService.getProfileFractionByProfile(profile);
    }

    @DeleteMapping("/delete/{profile}")
    public void deleteProfileFraction(@PathVariable(value = "profile") String profile) {
        profileFractionQueryService.deleteProfileFraction(profile);
    }

}
