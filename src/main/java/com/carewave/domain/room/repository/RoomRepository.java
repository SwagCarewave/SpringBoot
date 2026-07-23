package com.carewave.domain.room.repository;

import com.carewave.domain.room.entity.Room;
import com.carewave.domain.room.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByRoomNumber(String roomNumber);

    boolean existsByRoomNumberAndIdNot(
            String roomNumber,
            Long roomId
    );
    long countByStatus(RoomStatus status);

    List<Room> findAllByStatusOrderByRoomNumberAsc(
            RoomStatus status
    );

    List<Room> findAllByOrderByRoomNumberAsc();
}