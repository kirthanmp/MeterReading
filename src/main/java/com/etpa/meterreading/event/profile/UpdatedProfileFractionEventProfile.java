package com.etpa.meterreading.event.profile;

import com.etpa.meterreading.event.ProfileBaseEvent;
import com.etpa.meterreading.utils.Status;

public class UpdatedProfileFractionEventProfile extends ProfileBaseEvent<String> {

    public final String profile;

    public final Status status;

    public UpdatedProfileFractionEventProfile(String id, String profile, Status status) {
        super(id);
        this.profile = profile;
        this.status = status;
    }

}
