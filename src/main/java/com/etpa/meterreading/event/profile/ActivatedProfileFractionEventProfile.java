package com.etpa.meterreading.event.profile;


import com.etpa.meterreading.event.ProfileBaseEvent;
import com.etpa.meterreading.utils.Status;

public class ActivatedProfileFractionEventProfile extends ProfileBaseEvent<String> {
    public final Status status;

    public ActivatedProfileFractionEventProfile(String id, Status status) {
        super(id);
        this.status = status;
    }
}
