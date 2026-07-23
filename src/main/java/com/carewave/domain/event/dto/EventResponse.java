package com.carewave.domain.event.dto;

import com.carewave.domain.event.entity.Event;
import com.carewave.domain.event.entity.EventStatus;
import com.carewave.domain.event.entity.EventType;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class EventResponse {

    private Long eventId;

    private EventType eventType;

    private String eventTypeName;

    private String description;

    private Long roomId;

    private String roomNumber;

    private OffsetDateTime occurredAt;

    private EventStatus confirmationStatus;

    private String evidenceSummary;

    private OffsetDateTime confirmedAt;

    public static EventResponse from(Event event) {
        return EventResponse.builder()
                .eventId(event.getId())
                .eventType(event.getEventType())
                .eventTypeName(
                        event.getEventType().getDisplayName()
                )
                .description(
                        event.getEventType().getDescription()
                )
                .roomId(event.getRoom().getId())
                .roomNumber(event.getRoom().getRoomNumber())
                .occurredAt(event.getOccurredAt())
                .confirmationStatus(event.getStatus())
                .evidenceSummary(event.getEvidenceSummary())
                .confirmedAt(event.getConfirmedAt())
                .build();
    }
}