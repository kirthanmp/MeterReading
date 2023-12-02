package com.etpa.meterreading.dto.meter;

import com.etpa.meterreading.utils.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeterReadingResponse {

    private String meterId;

    private String profile;

    private Status status;

}
