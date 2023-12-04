package com.etpa.meterreading.service.helper;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StatusMessage {

    private boolean isValid;

    private String message;
}
