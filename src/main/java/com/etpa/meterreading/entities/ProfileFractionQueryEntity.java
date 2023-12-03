package com.etpa.meterreading.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileFractionQueryEntity {

    @Id
    private String id;

    private String months;

    private String profile;

    private float fraction;

}
