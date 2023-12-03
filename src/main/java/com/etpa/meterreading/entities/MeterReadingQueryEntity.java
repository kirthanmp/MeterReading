package com.etpa.meterreading.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeterReadingQueryEntity {

    @Id
    private String id;

    private String meterID;

    private String profile;

    private String months;

    private int meterReading;
}
