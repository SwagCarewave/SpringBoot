package com.carewave.domain.notification.dto;

import com.carewave.domain.notification.entity.NotificationSetting;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationSettingResponse {

    private boolean browserNotificationEnabled;
    private double fallSensitivityThreshold;
    private int inactivityThresholdMinutes;
    private String emergencyContactName;
    private String emergencyContactPhone;

    public static NotificationSettingResponse from(
            NotificationSetting setting
    ) {
        return NotificationSettingResponse.builder()
                .browserNotificationEnabled(
                        setting.isBrowserNotificationEnabled()
                )
                .fallSensitivityThreshold(
                        setting.getFallSensitivityThreshold()
                )
                .inactivityThresholdMinutes(
                        setting.getInactivityThresholdMinutes()
                )
                .emergencyContactName(
                        setting.getEmergencyContactName()
                )
                .emergencyContactPhone(
                        setting.getEmergencyContactPhone()
                )
                .build();
    }
}