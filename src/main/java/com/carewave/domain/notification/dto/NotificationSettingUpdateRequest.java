package com.carewave.domain.notification.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationSettingUpdateRequest {

    @NotNull(
            message = "브라우저 알림 활성화 여부는 필수입니다."
    )
    private Boolean browserNotificationEnabled;

    @NotNull(
            message = "낙상 감지 민감도는 필수입니다."
    )
    @DecimalMin(
            value = "0.0",
            message = "낙상 감지 민감도는 0.0 이상이어야 합니다."
    )
    @DecimalMax(
            value = "1.0",
            message = "낙상 감지 민감도는 1.0 이하여야 합니다."
    )
    private Double fallSensitivityThreshold;

    @NotNull(
            message = "무활동 판단 시간은 필수입니다."
    )
    @Min(
            value = 1,
            message = "무활동 판단 시간은 1분 이상이어야 합니다."
    )
    @Max(
            value = 1440,
            message = "무활동 판단 시간은 1440분 이하여야 합니다."
    )
    private Integer inactivityThresholdMinutes;

    @Size(
            max = 50,
            message = "비상 연락 담당자 이름은 50자 이하여야 합니다."
    )
    private String emergencyContactName;

    @Size(
            max = 20,
            message = "비상 연락처는 20자 이하여야 합니다."
    )
    @Pattern(
            regexp = "^\\s*$|^[0-9-]+$",
            message = "비상 연락처는 숫자와 하이픈(-)만 입력할 수 있습니다."
    )
    private String emergencyContactPhone;

}