package com.etpa.meterreading.controller.meter;

import com.etpa.meterreading.entities.MeterReadingQueryEntity;
import com.etpa.meterreading.service.meter.MeterReadingQueryService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meter-reading")
@Api(value = "MeterReading Queries",
        description = "MeterReading Queries Related Endpoints",
        tags = "MeterReading Queries")
public class MeterReadingQueryController {

    private final MeterReadingQueryService meterReadingQueryService;

    public MeterReadingQueryController(MeterReadingQueryService meterReadingQueryService) {
        this.meterReadingQueryService = meterReadingQueryService;
    }

    @GetMapping("{meterReadingId}")
    public MeterReadingQueryEntity getMeterReadingById(@PathVariable(value = "meterReadingId") String meterReadingId) {
        return meterReadingQueryService.getMeterReading(meterReadingId);
    }

    @GetMapping("/profile/{profile}")
    public List<MeterReadingQueryEntity> getMeterReadingByProfile(@PathVariable(value = "profile") String profile) {
        return meterReadingQueryService.getMeterReadingByProfile(profile);
    }

    @GetMapping("/{meterReadingId}/events")
    public List<Object> listEventsForMeterReading(@PathVariable(value = "meterReadingId") String meterReadingId) {
        return meterReadingQueryService.listEventsForMeterReading(meterReadingId);
    }

}
