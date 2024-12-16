package com.api.product_control.dtos;

import java.time.LocalDateTime;

public record ExceptionRecordDto(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {
}
