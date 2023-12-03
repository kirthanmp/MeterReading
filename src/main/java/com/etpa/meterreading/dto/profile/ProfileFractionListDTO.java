package com.etpa.meterreading.dto.profile;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProfileFractionListDTO {

    private String profile;

    private List<FractionDTO> profileFractionDTOList;

}
