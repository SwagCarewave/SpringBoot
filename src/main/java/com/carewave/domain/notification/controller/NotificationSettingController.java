package com.carewave.domain.notification.controller;

import com.carewave.domain.notification.dto.NotificationSettingResponse;
import com.carewave.domain.notification.dto.NotificationSettingUpdateRequest;
import com.carewave.domain.notification.dto.TestNotificationRequest;
import com.carewave.domain.notification.dto.TestNotificationResponse;
import com.carewave.domain.notification.service.NotificationSettingService;
import com.carewave.global.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings/notifications")
@RequiredArgsConstructor
public class NotificationSettingController {

    private final NotificationSettingService
            notificationSettingService;

    @GetMapping
    public ApiResponse<NotificationSettingResponse>
    getNotificationSetting() {
        return ApiResponse.success(
                notificationSettingService.getSetting()
        );
    }

    @PutMapping
    public ApiResponse<NotificationSettingResponse>
    updateNotificationSetting(
            @Valid @RequestBody
            NotificationSettingUpdateRequest request
    ) {
        return ApiResponse.success(
                notificationSettingService
                        .updateSetting(request)
        );
    }

    @PostMapping("/test")
    public ApiResponse<TestNotificationResponse>
    createTestNotification(
            @Valid @RequestBody
            TestNotificationRequest request
    ) {
        return ApiResponse.success(
                notificationSettingService
                        .createTestNotification(request)
        );
    }
}