package com.etpa.meterreading.aggregate.profile;

import com.etpa.meterreading.command.profile.ProfileFractionCreateProfileCommand;
import com.etpa.meterreading.command.profile.ProfileFractionDeleteProfileCommand;
import com.etpa.meterreading.command.profile.ProfileFractionUpdateProfileCommand;
import com.etpa.meterreading.event.profile.ActivatedProfileFractionEventProfile;
import com.etpa.meterreading.event.profile.DeletedProfileFractionEventProfile;
import com.etpa.meterreading.event.profile.ProfileFractionCreateEventProfile;
import com.etpa.meterreading.event.profile.ProfileFractionDeleteEventProfile;
import com.etpa.meterreading.event.profile.ProfileFractionUpdateEventProfile;
import com.etpa.meterreading.event.profile.UpdatedProfileFractionEventProfile;
import com.etpa.meterreading.utils.Status;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Data
public class ProfileFractionAggregate {

    @AggregateIdentifier
    private String id;

    private String months;

    private String profile;

    private float fraction;

    public ProfileFractionAggregate(){}

    @CommandHandler
    public ProfileFractionAggregate(ProfileFractionCreateProfileCommand profileFractionCreateCommand) {
        AggregateLifecycle.apply(new ProfileFractionCreateEventProfile(
                profileFractionCreateCommand.id,
                profileFractionCreateCommand.months,
                profileFractionCreateCommand.profile,
                profileFractionCreateCommand.fraction));
    }

    @EventSourcingHandler
    protected void on(ProfileFractionCreateEventProfile profileFractionCreateEvent) {
        this.id = profileFractionCreateEvent.id;
        this.months = profileFractionCreateEvent.months;
        this.profile = profileFractionCreateEvent.profile;
        this.fraction = profileFractionCreateEvent.fraction;
        AggregateLifecycle.apply(new ActivatedProfileFractionEventProfile(this.id, Status.CREATED));
    }

    @CommandHandler
    public ProfileFractionAggregate(ProfileFractionUpdateProfileCommand profileFractionUpdateCommand) {
        AggregateLifecycle.apply(new ProfileFractionUpdateEventProfile(
                profileFractionUpdateCommand.id,
                profileFractionUpdateCommand.months,
                profileFractionUpdateCommand.profile,
                profileFractionUpdateCommand.fraction));
    }

    @EventSourcingHandler
    protected void on(ProfileFractionUpdateEventProfile profileFractionUpdateEvent) {
        this.id = profileFractionUpdateEvent.id;
        this.months = profileFractionUpdateEvent.months;
        this.profile = profileFractionUpdateEvent.profile;
        this.fraction = profileFractionUpdateEvent.fraction;
        AggregateLifecycle.apply(new UpdatedProfileFractionEventProfile(this.id, this.profile, Status.UPDATED));
    }

    @CommandHandler
    public ProfileFractionAggregate(ProfileFractionDeleteProfileCommand profileFractionDeleteCommand) {
        this.id = profileFractionDeleteCommand.id;
        AggregateLifecycle.apply(new ProfileFractionDeleteEventProfile (
                profileFractionDeleteCommand.id,
                profileFractionDeleteCommand.months,
                profileFractionDeleteCommand.profile,
                profileFractionDeleteCommand.fraction));
    }

    @EventSourcingHandler
    protected void on(ProfileFractionDeleteEventProfile profileFractionDeleteEvent) {
        this.id = profileFractionDeleteEvent.id;
        this.months = profileFractionDeleteEvent.months;
        this.profile = profileFractionDeleteEvent.profile;
        this.fraction = profileFractionDeleteEvent.fraction;
        AggregateLifecycle.apply(new DeletedProfileFractionEventProfile(this.id, this.profile, Status.DELETED));
    }

}
