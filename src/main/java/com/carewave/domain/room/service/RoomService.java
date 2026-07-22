package com.carewave.domain.room.service;

import com.carewave.domain.room.dto.RoomCreateRequest;
import com.carewave.domain.room.dto.RoomResponse;
import com.carewave.domain.room.dto.RoomStatusUpdateRequest;
import com.carewave.domain.room.dto.RoomUpdateRequest;
import com.carewave.domain.room.entity.Room;
import com.carewave.domain.room.entity.RoomStatus;
import com.carewave.domain.room.exception.RoomErrorCode;
import com.carewave.domain.room.repository.RoomRepository;
import com.carewave.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public RoomResponse createRoom(RoomCreateRequest request) {
        String roomNumber = normalizeRoomNumber(request.getRoomNumber());

        validateDuplicateRoomNumber(roomNumber);

        Room room = Room.create(
                roomNumber,
                normalizeDescription(request.getDescription())
        );

        try {
            Room savedRoom = roomRepository.saveAndFlush(room);

            return RoomResponse.from(savedRoom);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(RoomErrorCode.DUPLICATE_ROOM_NUMBER);
        }
    }

    public List<RoomResponse> getRooms(Boolean activeOnly) {
        List<Room> rooms;

        if (Boolean.TRUE.equals(activeOnly)) {
            rooms = roomRepository.findAllByStatusOrderByRoomNumberAsc(
                    RoomStatus.ACTIVE
            );
        } else {
            rooms = roomRepository.findAllByOrderByRoomNumberAsc();
        }

        return rooms.stream()
                .map(RoomResponse::from)
                .toList();
    }

    public RoomResponse getRoom(Long roomId) {
        Room room = findRoom(roomId);

        return RoomResponse.from(room);
    }

    @Transactional
    public RoomResponse updateRoom(
            Long roomId,
            RoomUpdateRequest request
    ) {
        Room room = findRoom(roomId);
        String roomNumber = normalizeRoomNumber(request.getRoomNumber());

        if (roomRepository.existsByRoomNumberAndIdNot(roomNumber, roomId)) {
            throw new CustomException(
                    RoomErrorCode.DUPLICATE_ROOM_NUMBER
            );
        }

        try {
            room.update(
                    roomNumber,
                    normalizeDescription(request.getDescription())
            );
            roomRepository.flush();

            return RoomResponse.from(room);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(RoomErrorCode.DUPLICATE_ROOM_NUMBER);
        }
    }

    @Transactional
    public RoomResponse updateRoomStatus(
            Long roomId,
            RoomStatusUpdateRequest request
    ) {
        Room room = findRoom(roomId);

        room.updateStatus(request.getStatus());

        return RoomResponse.from(room);
    }

    private Room findRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() ->
                        new CustomException(
                                RoomErrorCode.ROOM_NOT_FOUND
                        )
                );
    }

    private void validateDuplicateRoomNumber(String roomNumber) {
        if (roomRepository.existsByRoomNumber(roomNumber)) {
            throw new CustomException(
                    RoomErrorCode.DUPLICATE_ROOM_NUMBER
            );
        }
    }

    private String normalizeRoomNumber(String roomNumber) {
        return roomNumber.trim();
    }

    private String normalizeDescription(String description) {
        if (description == null || description.isBlank()) {
            return null;
        }

        return description.trim();
    }
}
