package com.carewave.domain.resident.repository;

import com.carewave.domain.resident.entity.Resident;
import com.carewave.domain.resident.entity.ResidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResidentRepository
        extends JpaRepository<Resident, Long> {

    List<Resident> findAllByStatusOrderByNameAsc(
            ResidentStatus status
    );

    List<Resident> findAllByOrderByNameAsc();

    Optional<Resident>
    findFirstByRoomIdAndStatusOrderByIdDesc(
            Long roomId,
            ResidentStatus status
    );
}