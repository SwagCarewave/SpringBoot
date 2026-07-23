package com.carewave.domain.notification.dto;

import com.carewave.domain.event.entity.Event;
import com.carewave.domain.event.entity.EventType;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class TestNotificationResponse {

    private Long eventId;
    private Long roomId;
    private EventType eventType;
    private String message;
    private OffsetDateTime occurredAt;

    public static TestNotificationResponse from(
            Event event
    ) {
        return TestNotificationResponse.builder()
                .eventId(event.getId())
                .roomId(event.getRoom().getId())
                .eventType(event.getEventType())
                .message(
                        "테스트 낙상 알림이 생성되었습니다."
                )
                .occurredAt(event.getOccurredAt())
                .build();
    }
}