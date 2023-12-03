package com.etpa.meterreading.dto.meter;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReadingDTO {

    private String months;

    private int meterReading;

}
