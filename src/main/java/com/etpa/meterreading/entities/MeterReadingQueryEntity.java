package com.etpa.meterreading.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class MeterReadingQueryEntity {

    @Id
    private String id;

    private String meterID;

    private String profile;

    private String months;

    private int meterReading;
}
