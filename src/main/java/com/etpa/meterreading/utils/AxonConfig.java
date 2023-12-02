package com.etpa.meterreading.utils;

import com.etpa.meterreading.aggregate.meter.MeterReadingAggregate;
import com.etpa.meterreading.aggregate.profile.ProfileFractionAggregate;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public EventStore eventStore() {
        return new EmbeddedEventStore.Builder()
                .storageEngine(new InMemoryEventStorageEngine())
                .build();
    }

    @Bean(name = "profileFractionAggregateRepository")
    public EventSourcingRepository<ProfileFractionAggregate> profileFractionAggregateRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(ProfileFractionAggregate.class)
                .eventStore(eventStore)
                .build();
    }

    @Bean(name = "meterReadingAggregateRepository")
    public EventSourcingRepository<MeterReadingAggregate> meterReadingAggregateRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(MeterReadingAggregate.class)
                .eventStore(eventStore)
                .build();
    }
}
