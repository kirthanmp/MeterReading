package com.etpa.meterreading.controller.meter;

import com.etpa.meterreading.dto.meter.MeterReadingListDTO;
import com.etpa.meterreading.dto.meter.MeterReadingResponse;
import com.etpa.meterreading.entities.MeterReadingQueryEntity;
import com.etpa.meterreading.service.meter.MeterReadingCommandService;
import com.etpa.meterreading.service.meter.MeterReadingQueryService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final MeterReadingQueryService meterReadingQueryService;

    public MeterReadingCommandController(MeterReadingCommandService meterReadingCommandService, MeterReadingQueryService meterReadingQueryService) {
        this.meterReadingCommandService = meterReadingCommandService;
        this.meterReadingQueryService = meterReadingQueryService;
    }

    @PostMapping(value = "/create")
    public CompletableFuture<List<MeterReadingResponse>> createMeterReading(@RequestBody List<MeterReadingListDTO> meterReadingListDTO) {
        return meterReadingCommandService.createMeterReading(meterReadingListDTO);
    }

    @PutMapping(value = "/update")
    public CompletableFuture<List<MeterReadingResponse>> updateMeterReading(@RequestBody List<MeterReadingListDTO> meterReadingListDTO) {
        return meterReadingCommandService.updateMeterReading(meterReadingListDTO);
    }

    @GetMapping("/meterId/{meterId}/months/{months}")
    public MeterReadingQueryEntity getMeterReadingByMeterIdAndMonths(@PathVariable(value = "meterId") String meterId,
                                                                     @PathVariable(value = "months") String months) {
        return meterReadingQueryService.getMeterReadingByMeterIDAndMonth(meterId, months);
    }

    @DeleteMapping("/{meterReadingId}")
    public MeterReadingResponse deleteMeterReading(@PathVariable(value = "meterReadingId") String meterReadingId) {
        return meterReadingQueryService.deleteMeterReading(meterReadingId);
    }

}
