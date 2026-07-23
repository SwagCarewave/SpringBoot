package com.carewave.domain.event.controller;

import com.carewave.domain.event.dto.EventCreateRequest;
import com.carewave.domain.event.dto.EventPageResponse;
import com.carewave.domain.event.dto.EventResponse;
import com.carewave.domain.event.dto.UnconfirmedEventCountResponse;
import com.carewave.domain.event.entity.EventStatus;
import com.carewave.domain.event.entity.EventType;
import com.carewave.domain.event.service.EventService;
import com.carewave.global.api.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /*
     * FastAPI가 감지 결과를 저장할 때 호출
     */
    @PostMapping
    public ApiResponse<EventResponse> createEvent(
            @Valid @RequestBody
            EventCreateRequest request
    ) {
        return ApiResponse.success(
                eventService.createEvent(request)
        );
    }

    /*
     * 알림 이력 목록
     *
     * 예:
     * GET /api/events
     *     ?startDate=2026-07-01
     *     &endDate=2026-07-23
     *     &eventTypes=FALL,ABNORMAL_BREATHING
     *     &status=UNCONFIRMED
     *     &page=0
     *     &size=10
     */
    @GetMapping
    public ApiResponse<EventPageResponse> getEvents(
            @RequestParam(required = false)
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE
            )
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE
            )
            LocalDate endDate,

            @RequestParam(required = false)
            List<EventType> eventTypes,

            @RequestParam(required = false)
            EventStatus status,

            @RequestParam(defaultValue = "0")
            @Min(
                    value = 0,
                    message = "page는 0 이상이어야 합니다."
            )
            int page,

            @RequestParam(defaultValue = "10")
            @Min(
                    value = 1,
                    message = "size는 1 이상이어야 합니다."
            )
            @Max(
                    value = 100,
                    message = "size는 100 이하여야 합니다."
            )
            int size
    ) {
        return ApiResponse.success(
                eventService.getEvents(
                        startDate,
                        endDate,
                        eventTypes,
                        status,
                        page,
                        size
                )
        );
    }

    /*
     * 화면 왼쪽 상단의 미확인 이벤트 개수
     */
    @GetMapping("/unconfirmed/count")
    public ApiResponse<UnconfirmedEventCountResponse>
    getUnconfirmedEventCount() {
        return ApiResponse.success(
                eventService.getUnconfirmedEventCount()
        );
    }

    /*
     * 필터 조건에 해당하는 이벤트 CSV 다운로드
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportEvents(
            @RequestParam(required = false)
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE
            )
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE
            )
            LocalDate endDate,

            @RequestParam(required = false)
            List<EventType> eventTypes,

            @RequestParam(required = false)
            EventStatus status
    ) {
        byte[] csv = eventService.exportEvents(
                startDate,
                endDate,
                eventTypes,
                status
        );

        ContentDisposition disposition =
                ContentDisposition
                        .attachment()
                        .filename(
                                "carewave-events.csv",
                                StandardCharsets.UTF_8
                        )
                        .build();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        disposition.toString()
                )
                .contentType(
                        new MediaType(
                                "text",
                                "csv",
                                StandardCharsets.UTF_8
                        )
                )
                .contentLength(csv.length)
                .body(csv);
    }

    /*
     * 우측 이벤트 상세 패널
     */
    @GetMapping("/{eventId}")
    public ApiResponse<EventResponse> getEvent(
            @PathVariable Long eventId
    ) {
        return ApiResponse.success(
                eventService.getEvent(eventId)
        );
    }

    /*
     * 이벤트 확인 완료 처리
     */
    @PatchMapping("/{eventId}/confirmation")
    public ApiResponse<EventResponse> confirmEvent(
            @PathVariable Long eventId
    ) {
        return ApiResponse.success(
                eventService.confirmEvent(eventId)
        );
    }
}