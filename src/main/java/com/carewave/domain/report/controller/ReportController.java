package com.carewave.domain.report.controller;

import com.carewave.domain.report.dto.ReportDetailResponse;
import com.carewave.domain.report.dto.ReportListResponse;
import com.carewave.domain.report.dto.ReportRegenerateRequest;
import com.carewave.domain.report.service.ReportService;
import com.carewave.global.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ApiResponse<List<ReportListResponse>>
    getReports() {
        return ApiResponse.success(
                reportService.getReports()
        );
    }

    @GetMapping("/{reportId}")
    public ApiResponse<ReportDetailResponse>
    getReport(
            @PathVariable Long reportId
    ) {
        return ApiResponse.success(
                reportService.getReport(reportId)
        );
    }

    @PostMapping("/regenerate")
    public ApiResponse<ReportDetailResponse>
    regenerateReport(
            @Valid @RequestBody
            ReportRegenerateRequest request
    ) {
        return ApiResponse.success(
                reportService.regenerateReport(request)
        );
    }
}