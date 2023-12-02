package com.etpa.meterreading.service.meter;

import com.etpa.meterreading.entities.MeterReadingQueryEntity;
import com.etpa.meterreading.repository.MeterReadingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeterReadingQueryServiceImpl implements MeterReadingQueryService {

    private final EventStore eventStore;

    public MeterReadingQueryServiceImpl(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Autowired
    private MeterReadingRepository meterReadingRepository;

    @Override
    public List<Object> listEventsForMeterReading(String meterID) {
        return eventStore.readEvents(meterID)
                .asStream().map(s -> s.getPayload()).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public MeterReadingQueryEntity getMeterReading(String meterID) {
        return meterReadingRepository.findById(meterID).get();
    }

    @Override
    public List<MeterReadingQueryEntity> getMeterReadingByProfile(String profile) {
        return meterReadingRepository.findMeterReadingQueryEntitiesByProfile(profile);
    }

    @Override
    public List<MeterReadingQueryEntity> getMeterReadingByMeterID(String meterID) {
        return meterReadingRepository.findMeterReadingQueryEntityByMeterID(meterID);
    }

}
