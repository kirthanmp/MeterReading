package com.etpa.meterreading.command.profile;

import com.etpa.meterreading.command.BaseProfileCommand;

public class ProfileFractionDeleteProfileCommand extends BaseProfileCommand<String> {

    public final String months;

    public final String profile;

    public final float fraction;
    public ProfileFractionDeleteProfileCommand(String id, String months, String profile, float fraction) {
        super(id);
        this.months = months;
        this.profile = profile;
        this.fraction = fraction;
    }
}