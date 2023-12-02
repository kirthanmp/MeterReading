package com.etpa.meterreading.dto.profile;

import com.etpa.meterreading.utils.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileFractionResponse {

    private String profile;
    private Status status;
    private String message;

}
