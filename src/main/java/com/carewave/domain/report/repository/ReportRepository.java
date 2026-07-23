package com.carewave.domain.report.repository;

import com.carewave.domain.report.entity.Report;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReportRepository
        extends JpaRepository<Report, Long> {

    @EntityGraph(
            attributePaths = {
                    "resident",
                    "resident.room"
            }
    )
    List<Report> findAllByOrderByGeneratedAtDesc();

    @EntityGraph(
            attributePaths = {
                    "resident",
                    "resident.room"
            }
    )
    Optional<Report> findDetailById(Long id);

    Optional<Report>
    findByResidentIdAndPeriodStartAndPeriodEnd(
            Long residentId,
            LocalDate periodStart,
            LocalDate periodEnd
    );
}