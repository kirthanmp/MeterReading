package com.etpa.meterreading.controller.meter;

import com.etpa.meterreading.dto.meter.MeterReadingDTO;
import com.etpa.meterreading.dto.meter.MeterReadingListDTO;
import com.etpa.meterreading.dto.meter.MeterReadingResponse;
import com.etpa.meterreading.service.meter.MeterReadingCommandService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/meter-reading")
@Api(value = "MeterReading Commands",
        description = "MeterReading Commands Related Endpoints",
        tags = "MeterReading Commands")
public class MeterReadingCommandController {

    private final MeterReadingCommandService meterReadingCommandService;

    public MeterReadingCommandController(MeterReadingCommandService meterReadingCommandService) {
        this.meterReadingCommandService = meterReadingCommandService;
    }

    @PostMapping(value = "/create")
    public CompletableFuture<List<MeterReadingResponse>> createMeterReading(@RequestBody List<MeterReadingListDTO> meterReadingListDTO) {
        return meterReadingCommandService.createMeterReading(meterReadingListDTO);
    }

    @PutMapping(value = "/update/{meterReadingId}")
    public CompletableFuture<String> updateMeterReading(@PathVariable(value = "meterReadingId") String meterReadingId,
                                                        @RequestBody MeterReadingDTO meterReadingDTO) {
        return meterReadingCommandService.updateMeterReading(meterReadingId, meterReadingDTO);
    }

    @DeleteMapping(value = "/delete/{meterReadingId}")
    public CompletableFuture<String> deleteMeterReading(@PathVariable(value = "meterReadingId") String meterReadingId) {
        return meterReadingCommandService.deleteMeterReading(meterReadingId);
    }

}
