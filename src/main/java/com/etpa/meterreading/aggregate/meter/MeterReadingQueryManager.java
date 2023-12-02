package com.etpa.meterreading.aggregate.meter;

import com.etpa.meterreading.entities.MeterReadingQueryEntity;

import com.etpa.meterreading.event.MeterBaseEvent;
import com.etpa.meterreading.repository.MeterReadingRepository;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MeterReadingQueryManager {

    @Autowired
    private MeterReadingRepository meterReadingRepository;

    @Autowired
    @Qualifier("meterReadingAggregateRepository")
    private EventSourcingRepository<MeterReadingAggregate> meterReadingAggregateRepository;

    @EventSourcingHandler
    void on(MeterBaseEvent event) {
        persistMeterReading(buildQueryMeterReading(getMeterReadingFromEvent(event)));
    }

    private MeterReadingQueryEntity buildQueryMeterReading(MeterReadingAggregate meterReadingFromEvent) {
        MeterReadingQueryEntity meterReadingQueryEntity = findExistingOrCreateQueryMeterReading(meterReadingFromEvent.getId());

        meterReadingQueryEntity.setId(meterReadingFromEvent.getId());
        meterReadingQueryEntity.setMeterID(meterReadingFromEvent.getMeterId());
        meterReadingQueryEntity.setProfile(meterReadingFromEvent.getProfile());
        meterReadingQueryEntity.setMonths(meterReadingFromEvent.getMonths());
        meterReadingQueryEntity.setMeterReading(meterReadingFromEvent.getMeterReading());

        return meterReadingQueryEntity;
    }

    private MeterReadingAggregate getMeterReadingFromEvent(MeterBaseEvent event) {
        return meterReadingAggregateRepository.load(event.id.toString()).getWrappedAggregate().getAggregateRoot();
    }

    private MeterReadingQueryEntity findExistingOrCreateQueryMeterReading(String id) {
        return meterReadingRepository.findById(id).isPresent() ?
               meterReadingRepository.findById(id).get() : new MeterReadingQueryEntity();
    }

    private void persistMeterReading(MeterReadingQueryEntity meterReadingQueryEntity) {
        meterReadingRepository.save(meterReadingQueryEntity);
    }

}
