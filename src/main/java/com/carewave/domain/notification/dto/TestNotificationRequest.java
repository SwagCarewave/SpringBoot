package com.carewave.domain.notification.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestNotificationRequest {

    @NotNull(message = "방 ID는 필수입니다.")
    private Long roomId;
}