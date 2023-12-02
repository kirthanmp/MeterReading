package com.etpa.meterreading.dto.meter;

import lombok.Data;

import java.util.List;

@Data
public class MeterReadingListDTO {

    private String meterId;
    private String profile;

    private List<ReadingDTO> readingDTOList;

}
