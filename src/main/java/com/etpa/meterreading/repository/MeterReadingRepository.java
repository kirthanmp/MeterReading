package com.etpa.meterreading.repository;

import com.etpa.meterreading.entities.MeterReadingQueryEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterReadingRepository extends CrudRepository<MeterReadingQueryEntity, String>,
        JpaSpecificationExecutor<MeterReadingQueryEntity>
    {

    List<MeterReadingQueryEntity> findMeterReadingQueryEntityByProfile(String profile);


    List<MeterReadingQueryEntity> findMeterReadingQueryEntityByMeterID(String meterID);

    MeterReadingQueryEntity findMeterReadingQueryEntityByMeterIDAndMonths(String meterID, String month);


}
