package com.carewave.domain.event.repository;

import com.carewave.domain.event.entity.Event;
import com.carewave.domain.event.entity.EventStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends
        JpaRepository<Event, Long>,
        JpaSpecificationExecutor<Event> {

    long countByStatus(EventStatus status);

    @EntityGraph(attributePaths = "room")
    @Query("""
            SELECT e
            FROM Event e
            WHERE e.id = :eventId
            """)
    Optional<Event> findDetailById(
            @Param("eventId") Long eventId
    );

    /*
     * UNCONFIRMED 상태인 이벤트만 CONFIRMED로 변경한다.
     *
     * 같은 이벤트에 동시에 여러 확인 요청이 들어와도
     * 최초 한 요청만 1건을 수정하고, 나머지는 0건을 반환한다.
     */
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

    @Override
    @EntityGraph(attributePaths = "room")
    Page<Event> findAll(
            Specification<Event> specification,
            Pageable pageable
    );

    @Override
    @EntityGraph(attributePaths = "room")
    List<Event> findAll(
            Specification<Event> specification,
            Sort sort
    );
}