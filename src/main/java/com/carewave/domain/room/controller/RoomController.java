package com.carewave.domain.room.controller;

import com.carewave.domain.device.dto.DeviceResponse;
import com.carewave.domain.device.service.DeviceService;
import com.carewave.domain.room.dto.RoomCreateRequest;
import com.carewave.domain.room.dto.RoomResponse;
import com.carewave.domain.room.dto.RoomStatusUpdateRequest;
import com.carewave.domain.room.dto.RoomUpdateRequest;
import com.carewave.domain.room.service.RoomService;
import com.carewave.global.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final DeviceService deviceService;

    @PostMapping
    public ApiResponse<RoomResponse> createRoom(
            @Valid @RequestBody RoomCreateRequest request
    ) {
        return ApiResponse.success(
                roomService.createRoom(request)
        );
    }

    @GetMapping
    public ApiResponse<List<RoomResponse>> getRooms(
            @RequestParam(
                    required = false,
                    defaultValue = "true"
            ) Boolean activeOnly
    ) {
        return ApiResponse.success(
                roomService.getRooms(activeOnly)
        );
    }

    @GetMapping("/{roomId}")
    public ApiResponse<RoomResponse> getRoom(
            @PathVariable Long roomId
    ) {
        return ApiResponse.success(
                roomService.getRoom(roomId)
        );
    }

    @PatchMapping("/{roomId}")
    public ApiResponse<RoomResponse> updateRoom(
            @PathVariable Long roomId,
            @Valid @RequestBody RoomUpdateRequest request
    ) {
        return ApiResponse.success(
                roomService.updateRoom(roomId, request)
        );
    }

    @PatchMapping("/{roomId}/status")
    public ApiResponse<RoomResponse> updateRoomStatus(
            @PathVariable Long roomId,
            @Valid @RequestBody RoomStatusUpdateRequest request
    ) {
        return ApiResponse.success(
                roomService.updateRoomStatus(roomId, request)
        );
    }

    @GetMapping("/{roomId}/devices")
    public ApiResponse<List<DeviceResponse>> getRoomDevices(
            @PathVariable Long roomId
    ) {
        return ApiResponse.success(
                deviceService.getDevicesByRoom(roomId)
        );
    }
}