package com.carewave.domain.resident.service;

import com.carewave.domain.resident.dto.ResidentCreateRequest;
import com.carewave.domain.resident.dto.ResidentResponse;
import com.carewave.domain.resident.dto.ResidentStatusUpdateRequest;
import com.carewave.domain.resident.dto.ResidentUpdateRequest;
import com.carewave.domain.resident.entity.Resident;
import com.carewave.domain.resident.entity.ResidentStatus;
import com.carewave.domain.resident.exception.ResidentErrorCode;
import com.carewave.domain.resident.repository.ResidentRepository;
import com.carewave.domain.room.entity.Room;
import com.carewave.domain.room.entity.RoomStatus;
import com.carewave.domain.room.exception.RoomErrorCode;
import com.carewave.domain.room.repository.RoomRepository;
import com.carewave.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResidentService {

    private final ResidentRepository residentRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public ResidentResponse createResident(
            ResidentCreateRequest request
    ) {
        Room room = findAssignableRoom(request.getRoomId());

        Resident resident = Resident.create(
                normalizeRequired(request.getName()),
                request.getBirthDate(),
                request.getGender(),
                normalizeOptional(request.getPhoneNumber()),
                normalizeRequired(request.getGuardianName()),
                normalizeRequired(request.getGuardianPhoneNumber()),
                normalizeOptional(request.getNote()),
                room
        );

        Resident savedResident = residentRepository.save(resident);

        return ResidentResponse.from(savedResident);
    }

    public List<ResidentResponse> getResidents(Boolean activeOnly) {
        List<Resident> residents;

        if (Boolean.TRUE.equals(activeOnly)) {
            residents = residentRepository
                    .findAllByStatusOrderByNameAsc(
                            ResidentStatus.ACTIVE
                    );
        } else {
            residents = residentRepository
                    .findAllByOrderByNameAsc();
        }

        return residents.stream()
                .map(ResidentResponse::from)
                .toList();
    }

    public ResidentResponse getResident(Long residentId) {
        return ResidentResponse.from(
                findResident(residentId)
        );
    }

    @Transactional
    public ResidentResponse updateResident(
            Long residentId,
            ResidentUpdateRequest request
    ) {
        Resident resident = findResident(residentId);

        Room room = request.getRoomId() == null
                ? null
                : findAssignableRoom(request.getRoomId());

        resident.update(
                normalizeNullableRequired(request.getName()),
                request.getBirthDate(),
                request.getGender(),
                normalizeNullableOptional(request.getPhoneNumber()),
                normalizeNullableRequired(request.getGuardianName()),
                normalizeNullableRequired(request.getGuardianPhoneNumber()),
                normalizeNullableOptional(request.getNote()),
                room
        );

        return ResidentResponse.from(resident);
    }

    @Transactional
    public ResidentResponse updateResidentStatus(
            Long residentId,
            ResidentStatusUpdateRequest request
    ) {
        Resident resident = findResident(residentId);

        if (resident.getStatus() == request.getStatus()) {
            throw new CustomException(
                    ResidentErrorCode.RESIDENT_STATUS_ALREADY_SET
            );
        }

        resident.updateStatus(request.getStatus());

        return ResidentResponse.from(resident);
    }

    private Resident findResident(Long residentId) {
        return residentRepository.findById(residentId)
                .orElseThrow(() ->
                        new CustomException(
                                ResidentErrorCode.RESIDENT_NOT_FOUND
                        )
                );
    }

    private Room findAssignableRoom(Long roomId) {
        if (roomId == null) {
            return null;
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() ->
                        new CustomException(
                                RoomErrorCode.ROOM_NOT_FOUND
                        )
                );

        if (room.getStatus() != RoomStatus.ACTIVE) {
            throw new CustomException(
                    ResidentErrorCode.ROOM_NOT_ACTIVE
            );
        }

        return room;
    }

    private String normalizeRequired(String value) {
        return value.trim();
    }

    private String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    private String normalizeNullableRequired(String value) {
        if (value == null) {
            return null;
        }

        return value.trim();
    }

    private String normalizeNullableOptional(String value) {
        if (value == null) {
            return null;
        }

        if (value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}