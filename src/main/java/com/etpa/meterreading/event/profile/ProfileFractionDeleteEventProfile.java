package com.etpa.meterreading.event.profile;

import com.etpa.meterreading.event.ProfileBaseEvent;

public class ProfileFractionDeleteEventProfile extends ProfileBaseEvent<String> {

    public final String months;

    public final String profile;

    public final float fraction;
        public ProfileFractionDeleteEventProfile(String id, String months, String profile, float fraction) {
            super(id);
            this.months = months;
            this.profile = profile;
            this.fraction = fraction;
        }
}
