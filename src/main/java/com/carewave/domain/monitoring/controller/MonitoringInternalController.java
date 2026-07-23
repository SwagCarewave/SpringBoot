package com.carewave.domain.monitoring.controller;

import com.carewave.domain.monitoring.dto.MonitoringMeasurementRequest;
import com.carewave.domain.monitoring.dto.RoomMonitoringResponse;
import com.carewave.domain.monitoring.service.MonitoringService;
import com.carewave.global.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internal/monitoring")
@RequiredArgsConstructor
public class MonitoringInternalController {

    private final MonitoringService monitoringService;

    @PostMapping("/rooms/{roomId}/measurements")
    public ApiResponse<RoomMonitoringResponse>
    saveMeasurement(
            @PathVariable Long roomId,
            @Valid @RequestBody
            MonitoringMeasurementRequest request
    ) {
        return ApiResponse.success(
                monitoringService.saveMeasurement(
                        roomId,
                        request
                )
        );
    }
}