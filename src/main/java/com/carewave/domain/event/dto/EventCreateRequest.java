package com.carewave.domain.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
public class EventCreateRequest {

    @NotBlank(message = "event_type은 필수입니다.")
    @JsonProperty("event_type")
    private String eventType;

    @NotNull(message = "occurred_at은 필수입니다.")
    @JsonProperty("occurred_at")
    private OffsetDateTime occurredAt;

    @NotBlank(message = "status는 필수입니다.")
    private String status;
}