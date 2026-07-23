package com.carewave.domain.event.entity;

import com.carewave.common.BaseEntity;
import com.carewave.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "events",
        indexes = {
                @Index(
                        name = "idx_event_occurred_at",
                        columnList = "occurred_at"
                ),
                @Index(
                        name = "idx_event_type_status",
                        columnList = "event_type,status"
                ),
                @Index(
                        name = "idx_event_room_id",
                        columnList = "room_id"
                )
        }
)
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "event_type",
            nullable = false,
            length = 30
    )
    private EventType eventType;

    @Column(
            name = "occurred_at",
            nullable = false
    )
    private OffsetDateTime occurredAt;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private EventStatus status;

    @Column(
            name = "evidence_summary",
            length = 1000
    )
    private String evidenceSummary;

    @Column(name = "confirmed_at")
    private OffsetDateTime confirmedAt;

    private Event(
            Room room,
            EventType eventType,
            OffsetDateTime occurredAt,
            String evidenceSummary
    ) {
        this.room = room;
        this.eventType = eventType;
        this.occurredAt = occurredAt;
        this.status = EventStatus.UNCONFIRMED;
        this.evidenceSummary = evidenceSummary;
    }

    public static Event create(
            Room room,
            EventType eventType,
            OffsetDateTime occurredAt,
            String evidenceSummary
    ) {
        return new Event(
                room,
                eventType,
                occurredAt,
                evidenceSummary
        );
    }

    public void confirm(OffsetDateTime confirmedAt) {
        this.status = EventStatus.CONFIRMED;
        this.confirmedAt = confirmedAt;
    }
}