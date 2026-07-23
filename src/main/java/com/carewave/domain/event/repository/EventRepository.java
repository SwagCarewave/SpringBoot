package com.carewave.domain.event.repository;

import com.carewave.domain.event.entity.Event;
import com.carewave.domain.event.entity.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import com.carewave.domain.event.entity.EventType;

public interface EventRepository
        extends JpaRepository<Event, Long>,
        JpaSpecificationExecutor<Event> {

    long countByStatus(EventStatus status);

    long countByEventTypeAndStatus(
            EventType eventType,
            EventStatus status
    );

    @EntityGraph(attributePaths = "room")
    @Query("""
            SELECT e
            FROM Event e
            WHERE e.id = :eventId
            """)
    Optional<Event> findDetailById(
            @Param("eventId") Long eventId
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE Event e
            SET e.status = :confirmedStatus,
                e.confirmedAt = :confirmedAt
            WHERE e.id = :eventId
              AND e.status = :unconfirmedStatus
            """)
    int confirmIfUnconfirmed(
            @Param("eventId") Long eventId,
            @Param("unconfirmedStatus")
            EventStatus unconfirmedStatus,
            @Param("confirmedStatus")
            EventStatus confirmedStatus,
            @Param("confirmedAt")
            OffsetDateTime confirmedAt
    );

    // 이번에 추가할 메서드
    @EntityGraph(attributePaths = "room")
    Page<Event> findByRoomIdOrderByOccurredAtDesc(
            Long roomId,
            Pageable pageable
    );

    List<Event> findAllByRoomIdAndOccurredAtGreaterThanEqualAndOccurredAtLessThan(
            Long roomId,
            OffsetDateTime startAt,
            OffsetDateTime endExclusive
    );
}