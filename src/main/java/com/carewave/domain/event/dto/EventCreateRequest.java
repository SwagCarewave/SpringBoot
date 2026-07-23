package com.carewave.domain.event.dto;

import com.carewave.domain.event.entity.EventType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
public class EventCreateRequest {

    @NotNull(message = "room_id는 필수입니다.")
    @JsonProperty("room_id")
    private Long roomId;

    @NotNull(message = "event_type은 필수입니다.")
    @JsonProperty("event_type")
    private EventType eventType;

    @NotNull(message = "occurred_at은 필수입니다.")
    @JsonProperty("occurred_at")
    private OffsetDateTime occurredAt;

    @Size(
            max = 1000,
            message = "evidence_summary는 1000자 이하여야 합니다."
    )
    @JsonProperty("evidence_summary")
    private String evidenceSummary;
}