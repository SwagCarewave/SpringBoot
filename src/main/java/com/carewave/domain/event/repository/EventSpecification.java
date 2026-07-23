package com.carewave.domain.event.repository;

import com.carewave.domain.event.entity.Event;
import com.carewave.domain.event.entity.EventStatus;
import com.carewave.domain.event.entity.EventType;
import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;
import java.util.List;

public final class EventSpecification {

    private EventSpecification() {
    }

    public static Specification<Event>
    occurredAtGreaterThanOrEqualTo(
            OffsetDateTime start
    ) {
        return (root, query, criteriaBuilder) ->
                start == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.greaterThanOrEqualTo(
                        root.get("occurredAt"),
                        start
                );
    }

    public static Specification<Event> occurredAtLessThan(
            OffsetDateTime endExclusive
    ) {
        return (root, query, criteriaBuilder) ->
                endExclusive == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.lessThan(
                        root.get("occurredAt"),
                        endExclusive
                );
    }

    public static Specification<Event> eventTypeIn(
            List<EventType> eventTypes
    ) {
        return (root, query, criteriaBuilder) ->
                eventTypes == null || eventTypes.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : root.get("eventType").in(eventTypes);
    }

    public static Specification<Event> statusEquals(
            EventStatus status
    ) {
        return (root, query, criteriaBuilder) ->
                status == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(
                        root.get("status"),
                        status
                );
    }
}