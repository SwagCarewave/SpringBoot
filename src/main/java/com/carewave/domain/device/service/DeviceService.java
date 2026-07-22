package com.carewave.domain.device.service;

import com.carewave.domain.device.dto.*;
import com.carewave.domain.device.entity.Device;
import com.carewave.domain.device.entity.DeviceStatus;
import com.carewave.domain.device.exception.DeviceErrorCode;
import com.carewave.domain.device.repository.DeviceRepository;
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
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public DeviceResponse createDevice(
            DeviceCreateRequest request
    ) {
        String deviceCode = normalizeRequired(
                request.getDeviceCode()
        );

        validateDuplicateDeviceCode(deviceCode);

        Room room = request.getRoomId() == null
                ? null
                : findAssignableRoom(request.getRoomId());

        Device device = Device.create(
                deviceCode,
                normalizeRequired(request.getName()),
                request.getType(),
                normalizeOptional(request.getMacAddress()),
                normalizeOptional(request.getIpAddress()),
                request.getChannelNumber(),
                normalizeOptional(request.getDescription()),
                room
        );

        Device savedDevice = deviceRepository.save(device);

        return DeviceResponse.from(savedDevice);
    }

    public List<DeviceResponse> getDevices(Boolean activeOnly) {
        List<Device> devices;

        if (Boolean.TRUE.equals(activeOnly)) {
            devices = deviceRepository
                    .findAllByStatusOrderByDeviceCodeAsc(
                            DeviceStatus.ACTIVE
                    );
        } else {
            devices = deviceRepository
                    .findAllByOrderByDeviceCodeAsc();
        }

        return devices.stream()
                .map(DeviceResponse::from)
                .toList();
    }

    public DeviceResponse getDevice(Long deviceId) {
        return DeviceResponse.from(
                findDevice(deviceId)
        );
    }

    public List<DeviceResponse> getDevicesByRoom(
            Long roomId
    ) {
        findRoom(roomId);

        return deviceRepository
                .findAllByRoomIdOrderByDeviceCodeAsc(roomId)
                .stream()
                .map(DeviceResponse::from)
                .toList();
    }

    @Transactional
    public DeviceResponse updateDevice(
            Long deviceId,
            DeviceUpdateRequest request
    ) {
        Device device = findDevice(deviceId);

        String deviceCode = normalizeNullableRequired(
                request.getDeviceCode()
        );

        if (deviceCode != null
                && deviceRepository.existsByDeviceCodeAndIdNot(
                deviceCode,
                deviceId
        )) {
            throw new CustomException(
                    DeviceErrorCode.DUPLICATE_DEVICE_CODE
            );
        }

        device.update(
                deviceCode,
                normalizeNullableRequired(request.getName()),
                request.getType(),
                normalizeNullableOptional(request.getMacAddress()),
                normalizeNullableOptional(request.getIpAddress()),
                request.getChannelNumber(),
                normalizeNullableOptional(request.getDescription())
        );

        return DeviceResponse.from(device);
    }

    @Transactional
    public DeviceResponse updateDeviceStatus(
            Long deviceId,
            DeviceStatusUpdateRequest request
    ) {
        Device device = findDevice(deviceId);

        if (device.getStatus() == request.getStatus()) {
            throw new CustomException(
                    DeviceErrorCode.DEVICE_STATUS_ALREADY_SET
            );
        }

        device.updateStatus(request.getStatus());

        return DeviceResponse.from(device);
    }

    @Transactional
    public DeviceResponse assignRoom(
            Long deviceId,
            DeviceRoomAssignRequest request
    ) {
        Device device = findDevice(deviceId);
        Room room = findAssignableRoom(request.getRoomId());

        if (device.getRoom() != null
                && device.getRoom().getId().equals(room.getId())) {
            throw new CustomException(
                    DeviceErrorCode.DEVICE_ALREADY_ASSIGNED_TO_ROOM
            );
        }

        device.assignRoom(room);

        return DeviceResponse.from(device);
    }

    @Transactional
    public DeviceResponse unassignRoom(Long deviceId) {
        Device device = findDevice(deviceId);

        if (device.getRoom() == null) {
            throw new CustomException(
                    DeviceErrorCode.DEVICE_ROOM_NOT_ASSIGNED
            );
        }

        device.unassignRoom();

        return DeviceResponse.from(device);
    }

    private Device findDevice(Long deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() ->
                        new CustomException(
                                DeviceErrorCode.DEVICE_NOT_FOUND
                        )
                );
    }

    private Room findRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() ->
                        new CustomException(
                                RoomErrorCode.ROOM_NOT_FOUND
                        )
                );
    }

    private Room findAssignableRoom(Long roomId) {
        Room room = findRoom(roomId);

        if (room.getStatus() != RoomStatus.ACTIVE) {
            throw new CustomException(
                    DeviceErrorCode.ROOM_NOT_ACTIVE
            );
        }

        return room;
    }

    private void validateDuplicateDeviceCode(
            String deviceCode
    ) {
        if (deviceRepository.existsByDeviceCode(deviceCode)) {
            throw new CustomException(
                    DeviceErrorCode.DUPLICATE_DEVICE_CODE
            );
        }
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