package com.etpa.meterreading.repository;

import com.etpa.meterreading.entities.ProfileFractionQueryEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileFractionRepository extends CrudRepository<ProfileFractionQueryEntity, String>,
        JpaSpecificationExecutor<ProfileFractionQueryEntity> {

    List<ProfileFractionQueryEntity> findProfileFractionQueryEntityByProfile(String profile);

}
