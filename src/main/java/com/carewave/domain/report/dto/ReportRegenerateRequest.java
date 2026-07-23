package com.carewave.domain.report.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReportRegenerateRequest {

    @NotNull(message = "입소자 ID는 필수입니다.")
    private Long residentId;

    @NotNull(message = "리포트 시작일은 필수입니다.")
    private LocalDate periodStart;

    @NotNull(message = "리포트 종료일은 필수입니다.")
    private LocalDate periodEnd;
}