package com.carewave.domain.room.entity;

import com.carewave.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "rooms",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_room_number",
                        columnNames = "room_number"
                )
        }
)
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", nullable = false, length = 20)
    private String roomNumber;

    @Column(length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoomStatus status;

    private Room(
            String roomNumber,
            String description,
            RoomStatus status
    ) {
        this.roomNumber = roomNumber;
        this.description = description;
        this.status = status;
    }

    public static Room create(
            String roomNumber,
            String description
    ) {
        return new Room(
                roomNumber,
                description,
                RoomStatus.ACTIVE
        );
    }

    public void update(
            String roomNumber,
            String description
    ) {
        this.roomNumber = roomNumber;
        this.description = description;
    }

    public void updateStatus(RoomStatus status) {
        this.status = status;
    }
}