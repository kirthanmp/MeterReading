package com.etpa.meterreading.dto.meter;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MeterReadingListDTO {

    private String meterId;
    private String profile;

    private List<ReadingDTO> readingDTOList;

}
