package com.etpa.meterreading.dto.profile;

import lombok.Data;

import java.util.List;

@Data
public class ProfileFractionListDTO {

    private String profile;

    private List<FractionDTO> profileFractionDTOList;

}
