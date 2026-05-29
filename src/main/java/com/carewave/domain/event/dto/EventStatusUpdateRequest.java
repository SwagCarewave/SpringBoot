package com.carewave.domain.event.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventStatusUpdateRequest {

    @NotBlank(message = "status는 필수입니다.")
    private String status;
}