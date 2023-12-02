package com.etpa.meterreading.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProfileFractionQueryEntity {

    @Id
    private String id;

    private String months;

    private String profile;

    private float fraction;

}
