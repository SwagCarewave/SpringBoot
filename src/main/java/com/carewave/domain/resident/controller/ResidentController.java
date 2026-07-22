package com.carewave.domain.resident.controller;

import com.carewave.domain.resident.dto.ResidentCreateRequest;
import com.carewave.domain.resident.dto.ResidentResponse;
import com.carewave.domain.resident.dto.ResidentStatusUpdateRequest;
import com.carewave.domain.resident.dto.ResidentUpdateRequest;
import com.carewave.domain.resident.service.ResidentService;
import com.carewave.global.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
@RequiredArgsConstructor
public class ResidentController {

    private final ResidentService residentService;

    @GetMapping
    public ApiResponse<List<ResidentResponse>> getResidents(
            @RequestParam(
                    required = false,
                    defaultValue = "true"
            ) Boolean activeOnly
    ) {
        return ApiResponse.success(
                residentService.getResidents(activeOnly)
        );
    }

    @GetMapping("/{residentId}")
    public ApiResponse<ResidentResponse> getResident(
            @PathVariable Long residentId
    ) {
        return ApiResponse.success(
                residentService.getResident(residentId)
        );
    }

    @PostMapping
    public ApiResponse<ResidentResponse> createResident(
            @Valid @RequestBody ResidentCreateRequest request
    ) {
        return ApiResponse.success(
                residentService.createResident(request)
        );
    }

    @PatchMapping("/{residentId}")
    public ApiResponse<ResidentResponse> updateResident(
            @PathVariable Long residentId,
            @Valid @RequestBody ResidentUpdateRequest request
    ) {
        return ApiResponse.success(
                residentService.updateResident(
                        residentId,
                        request
                )
        );
    }

    @PatchMapping("/{residentId}/status")
    public ApiResponse<ResidentResponse> updateResidentStatus(
            @PathVariable Long residentId,
            @Valid @RequestBody ResidentStatusUpdateRequest request
    ) {
        return ApiResponse.success(
                residentService.updateResidentStatus(
                        residentId,
                        request
                )
        );
    }
}