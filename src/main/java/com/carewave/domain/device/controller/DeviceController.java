package com.carewave.domain.device.controller;

import com.carewave.domain.device.dto.*;
import com.carewave.domain.device.service.DeviceService;
import com.carewave.global.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    public ApiResponse<DeviceResponse> createDevice(
            @Valid @RequestBody DeviceCreateRequest request
    ) {
        return ApiResponse.success(
                deviceService.createDevice(request)
        );
    }

    @GetMapping
    public ApiResponse<List<DeviceResponse>> getDevices(
            @RequestParam(
                    required = false,
                    defaultValue = "true"
            ) Boolean activeOnly
    ) {
        return ApiResponse.success(
                deviceService.getDevices(activeOnly)
        );
    }

    @GetMapping("/{deviceId}")
    public ApiResponse<DeviceResponse> getDevice(
            @PathVariable Long deviceId
    ) {
        return ApiResponse.success(
                deviceService.getDevice(deviceId)
        );
    }

    @PatchMapping("/{deviceId}")
    public ApiResponse<DeviceResponse> updateDevice(
            @PathVariable Long deviceId,
            @Valid @RequestBody DeviceUpdateRequest request
    ) {
        return ApiResponse.success(
                deviceService.updateDevice(
                        deviceId,
                        request
                )
        );
    }

    @PatchMapping("/{deviceId}/status")
    public ApiResponse<DeviceResponse> updateDeviceStatus(
            @PathVariable Long deviceId,
            @Valid @RequestBody DeviceStatusUpdateRequest request
    ) {
        return ApiResponse.success(
                deviceService.updateDeviceStatus(
                        deviceId,
                        request
                )
        );
    }

    @PatchMapping("/{deviceId}/room")
    public ApiResponse<DeviceResponse> assignRoom(
            @PathVariable Long deviceId,
            @Valid @RequestBody DeviceRoomAssignRequest request
    ) {
        return ApiResponse.success(
                deviceService.assignRoom(
                        deviceId,
                        request
                )
        );
    }

    @DeleteMapping("/{deviceId}/room")
    public ApiResponse<DeviceResponse> unassignRoom(
            @PathVariable Long deviceId
    ) {
        return ApiResponse.success(
                deviceService.unassignRoom(deviceId)
        );
    }
}