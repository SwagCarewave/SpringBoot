package com.carewave.domain.system.controller;

import com.carewave.domain.system.dto.SystemStatusResponse;
import com.carewave.domain.system.service.SystemStatusService;
import com.carewave.global.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
public class SystemStatusController {

    private final SystemStatusService systemStatusService;

    @GetMapping("/status")
    public ApiResponse<SystemStatusResponse>
    getSystemStatus() {
        return ApiResponse.success(
                systemStatusService.getSystemStatus()
        );
    }
}