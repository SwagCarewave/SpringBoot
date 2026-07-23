package com.carewave.domain.event.controller;

import com.carewave.domain.event.dto.EventResponse;
import com.carewave.domain.event.service.EventService;
import com.carewave.global.api.ApiResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomEventController {

    private final EventService eventService;

    @GetMapping("/{roomId}/events/recent")
    public ApiResponse<List<EventResponse>>
    getRecentEvents(
            @PathVariable Long roomId,

            @RequestParam(defaultValue = "5")
            @Min(
                    value = 1,
                    message = "limit은 1 이상이어야 합니다."
            )
            @Max(
                    value = 20,
                    message = "limit은 20 이하여야 합니다."
            )
            int limit
    ) {
        return ApiResponse.success(
                eventService.getRecentEvents(
                        roomId,
                        limit
                )
        );
    }
}