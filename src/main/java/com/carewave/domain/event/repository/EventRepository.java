package com.carewave.domain.event.repository;

import com.carewave.domain.event.entity.Event;
import com.carewave.domain.event.entity.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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