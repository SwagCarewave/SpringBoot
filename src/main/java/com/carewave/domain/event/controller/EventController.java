package com.carewave.domain.event.controller;

import com.carewave.domain.event.dto.EventCreateRequest;
import com.carewave.domain.event.dto.EventResponse;
import com.carewave.domain.event.dto.EventStatusUpdateRequest;
import com.carewave.domain.event.service.EventService;
import com.carewave.global.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // FastAPI가 호출하는 API
    @PostMapping
    public ApiResponse<EventResponse> createEvent(
            @Valid @RequestBody EventCreateRequest request
    ) {
        return ApiResponse.success(eventService.createEvent(request));
    }

    // 전체 이벤트 조회
    @GetMapping
    public ApiResponse<List<EventResponse>> getEvents() {
        return ApiResponse.success(eventService.getEvents());
    }

    // 이벤트 단건 조회
    @GetMapping("/{eventId}")
    public ApiResponse<EventResponse> getEvent(
            @PathVariable Long eventId
    ) {
        return ApiResponse.success(eventService.getEvent(eventId));
    }

    // 확인 처리
    @PatchMapping("/{eventId}/status")
    public ApiResponse<EventResponse> updateEventStatus(
            @PathVariable Long eventId,
            @Valid @RequestBody EventStatusUpdateRequest request
    ) {
        return ApiResponse.success(
                eventService.updateEventStatus(eventId, request.getStatus())
        );
    }
}