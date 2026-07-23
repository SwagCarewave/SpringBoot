package com.carewave.domain.notification.entity;

import com.carewave.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notification_settings")
public class NotificationSetting extends BaseEntity {

    @Id
    private Long id;

    @Column(
            name = "browser_notification_enabled",
            nullable = false
    )
    private boolean browserNotificationEnabled;

    @Column(
            name = "fall_sensitivity_threshold",
            nullable = false
    )
    private double fallSensitivityThreshold;

    @Column(
            name = "inactivity_threshold_minutes",
            nullable = false
    )
    private int inactivityThresholdMinutes;

    @Column(
            name = "emergency_contact_name",
            length = 50
    )
    private String emergencyContactName;

    @Column(
            name = "emergency_contact_phone",
            length = 20
    )
    private String emergencyContactPhone;

    private NotificationSetting(
            boolean browserNotificationEnabled,
            double fallSensitivityThreshold,
            int inactivityThresholdMinutes
    ) {
        this.id = GLOBAL_SETTING_ID;
        this.browserNotificationEnabled =
                browserNotificationEnabled;
        this.fallSensitivityThreshold =
                fallSensitivityThreshold;
        this.inactivityThresholdMinutes =
                inactivityThresholdMinutes;
    }

    public static NotificationSetting createDefault() {
        return new NotificationSetting(
                true,
                0.8,
                30
        );
    }

    public static final Long GLOBAL_SETTING_ID = 1L;

    public void update(
            boolean browserNotificationEnabled,
            double fallSensitivityThreshold,
            int inactivityThresholdMinutes,
            String emergencyContactName,
            String emergencyContactPhone
    ) {
        this.browserNotificationEnabled =
                browserNotificationEnabled;
        this.fallSensitivityThreshold =
                fallSensitivityThreshold;
        this.inactivityThresholdMinutes =
                inactivityThresholdMinutes;
        this.emergencyContactName =
                emergencyContactName;
        this.emergencyContactPhone =
                emergencyContactPhone;
    }
}