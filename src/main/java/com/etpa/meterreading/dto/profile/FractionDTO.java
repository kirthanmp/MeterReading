package com.etpa.meterreading.dto.profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FractionDTO {

    private String months;

    private float fraction;

}
