package com.carewave.domain.monitoring.controller;

import com.carewave.domain.monitoring.dto.RoomMonitoringResponse;
import com.carewave.domain.monitoring.service.MonitoringService;
import com.carewave.global.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monitoring")
@RequiredArgsConstructor
public class MonitoringController {

    private final MonitoringService monitoringService;

    @GetMapping("/rooms/{roomId}")
    public ApiResponse<RoomMonitoringResponse>
    getRoomMonitoring(
            @PathVariable Long roomId
    ) {
        return ApiResponse.success(
                monitoringService.getLatestMeasurement(
                        roomId
                )
        );
    }
}