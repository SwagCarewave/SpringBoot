package com.carewave.domain.event.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {

    FALL(
            "낙상 감지",
            "낙상 위험으로 판단되어 알림이 발생했습니다."
    ),

    ABNORMAL_BREATHING(
            "이상 호흡",
            "호흡수 이상을 감지했습니다."
    ),

    PROLONGED_INACTIVITY(
            "장시간 무반응",
            "장시간 움직임이 감지되지 않았습니다."
    ),

    ANOMALY_WARNING(
            "이상탐지 사전경고",
            "평소와 다른 패턴이 감지되었습니다."
    );

    private final String displayName;
    private final String description;
}