package com.etpa.meterreading.aggregate.meter;

import com.etpa.meterreading.command.meter.MeterReadingCreateCommand;
import com.etpa.meterreading.command.meter.MeterReadingDeleteCommand;
import com.etpa.meterreading.command.meter.MeterReadingUpdateCommand;
import com.etpa.meterreading.event.meter.CreatedMeterReadingEvent;
import com.etpa.meterreading.event.meter.DeletedMeterReadingEvent;
import com.etpa.meterreading.event.meter.MeterReadingCreateEvent;
import com.etpa.meterreading.event.meter.MeterReadingDeleteEvent;
import com.etpa.meterreading.event.meter.MeterReadingUpdateEvent;
import com.etpa.meterreading.event.meter.UpdatedMeterReadingEvent;
import com.etpa.meterreading.utils.Status;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Data
public class MeterReadingAggregate {

    @AggregateIdentifier
    private String id;

    private String meterId;

    private String profile;

    private String months;

    private int meterReading;

    public MeterReadingAggregate(){}

    @CommandHandler
    public MeterReadingAggregate(MeterReadingCreateCommand meterReadingCreateCommand) {
        AggregateLifecycle.apply(new MeterReadingCreateEvent(
                meterReadingCreateCommand.id,
                meterReadingCreateCommand.meterId,
                meterReadingCreateCommand.profile,
                meterReadingCreateCommand.months,
                meterReadingCreateCommand.meterReading));
    }

    @EventSourcingHandler
    protected void on(MeterReadingCreateEvent meterReadingCreateEvent) {
        this.id = meterReadingCreateEvent.id;
        this.meterId = meterReadingCreateEvent.meterId;
        this.profile = meterReadingCreateEvent.profile;
        this.months = meterReadingCreateEvent.months;
        this.meterReading = meterReadingCreateEvent.meterReading;
        AggregateLifecycle.apply(new CreatedMeterReadingEvent(this.id, Status.CREATED));
    }

    @CommandHandler
    public MeterReadingAggregate(MeterReadingUpdateCommand meterReadingUpdateCommand) {
        AggregateLifecycle.apply(new MeterReadingUpdateEvent(
                meterReadingUpdateCommand.id,
                meterReadingUpdateCommand.meterId,
                meterReadingUpdateCommand.profile,
                meterReadingUpdateCommand.months,
                meterReadingUpdateCommand.meterReading));
    }

    @EventSourcingHandler
    protected void on(MeterReadingUpdateEvent meterReadingUpdateEvent) {
        this.id = meterReadingUpdateEvent.id;
        this.meterId = meterReadingUpdateEvent.meterId;
        this.profile = meterReadingUpdateEvent.profile;
        this.months = meterReadingUpdateEvent.months;
        this.meterReading = meterReadingUpdateEvent.meterReading;
        AggregateLifecycle.apply(new UpdatedMeterReadingEvent(this.id, Status.UPDATED));
    }

    @CommandHandler
    public MeterReadingAggregate(MeterReadingDeleteCommand meterReadingDeleteCommand) {
        AggregateLifecycle.apply(new MeterReadingDeleteEvent(
                meterReadingDeleteCommand.id));
    }

    @EventSourcingHandler
    protected void on(MeterReadingDeleteEvent meterReadingDeleteEvent) {
        this.id = meterReadingDeleteEvent.id;
        AggregateLifecycle.apply(new DeletedMeterReadingEvent(this.id, Status.DELETED));
    }

}
