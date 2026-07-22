package com.carewave.domain.device.entity;

import com.carewave.common.BaseEntity;
import com.carewave.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "devices",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_device_code",
                        columnNames = "device_code"
                )
        },
        indexes = {
                @Index(
                        name = "idx_device_room_id",
                        columnList = "room_id"
                ),
                @Index(
                        name = "idx_device_status",
                        columnList = "status"
                ),
                @Index(
                        name = "idx_device_type",
                        columnList = "type"
                )
        }
)
public class Device extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "device_code",
            nullable = false,
            length = 50
    )
    private String deviceCode;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DeviceType type;

    @Column(name = "mac_address", length = 50)
    private String macAddress;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "channel_number")
    private Integer channelNumber;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DeviceStatus status;

    private Device(
            String deviceCode,
            String name,
            DeviceType type,
            String macAddress,
            String ipAddress,
            Integer channelNumber,
            String description,
            Room room,
            DeviceStatus status
    ) {
        this.deviceCode = deviceCode;
        this.name = name;
        this.type = type;
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.channelNumber = channelNumber;
        this.description = description;
        this.room = room;
        this.status = status;
    }

    public static Device create(
            String deviceCode,
            String name,
            DeviceType type,
            String macAddress,
            String ipAddress,
            Integer channelNumber,
            String description,
            Room room
    ) {
        return new Device(
                deviceCode,
                name,
                type,
                macAddress,
                ipAddress,
                channelNumber,
                description,
                room,
                DeviceStatus.ACTIVE
        );
    }

    public void update(
            String deviceCode,
            String name,
            DeviceType type,
            String macAddress,
            String ipAddress,
            Integer channelNumber,
            String description
    ) {
        if (deviceCode != null) {
            this.deviceCode = deviceCode;
        }

        if (name != null) {
            this.name = name;
        }

        if (type != null) {
            this.type = type;
        }

        if (macAddress != null) {
            this.macAddress = macAddress;
        }

        if (ipAddress != null) {
            this.ipAddress = ipAddress;
        }

        if (channelNumber != null) {
            this.channelNumber = channelNumber;
        }

        if (description != null) {
            this.description = description;
        }
    }

    public void updateStatus(DeviceStatus status) {
        this.status = status;
    }

    public void assignRoom(Room room) {
        this.room = room;
    }

    public void unassignRoom() {
        this.room = null;
    }
}