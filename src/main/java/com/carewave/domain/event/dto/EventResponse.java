package com.carewave.domain.event.dto;

import com.carewave.domain.event.entity.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class EventResponse {

    private Long eventId;
    private String eventType;
    private OffsetDateTime occurredAt;
    private String status;

    public static EventResponse from(Event event) {
        return EventResponse.builder()
                .eventId(event.getId())
                .eventType(event.getEventType())
                .occurredAt(event.getOccurredAt())
                .status(event.getStatus())
                .build();
    }
}