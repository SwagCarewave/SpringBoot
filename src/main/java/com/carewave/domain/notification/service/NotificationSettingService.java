package com.carewave.domain.notification.service;

import com.carewave.domain.event.entity.Event;
import com.carewave.domain.event.entity.EventType;
import com.carewave.domain.event.repository.EventRepository;
import com.carewave.domain.notification.dto.NotificationSettingResponse;
import com.carewave.domain.notification.dto.NotificationSettingUpdateRequest;
import com.carewave.domain.notification.dto.TestNotificationRequest;
import com.carewave.domain.notification.dto.TestNotificationResponse;
import com.carewave.domain.notification.entity.NotificationSetting;
import com.carewave.domain.notification.repository.NotificationSettingRepository;
import com.carewave.domain.room.entity.Room;
import com.carewave.domain.room.exception.RoomErrorCode;
import com.carewave.domain.room.repository.RoomRepository;
import com.carewave.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationSettingService {

    private static final ZoneId KOREA_ZONE =
            ZoneId.of("Asia/Seoul");

    private static final String TEST_EVIDENCE_SUMMARY =
            "알림 설정 화면에서 생성한 테스트 낙상 이벤트입니다.";

    private final NotificationSettingRepository
            notificationSettingRepository;

    private final RoomRepository roomRepository;

    private final EventRepository eventRepository;

    @Transactional
    public NotificationSettingResponse getSetting() {
        NotificationSetting setting =
                findOrCreateSetting();

        return NotificationSettingResponse.from(setting);
    }

    @Transactional
    public NotificationSettingResponse updateSetting(
            NotificationSettingUpdateRequest request
    ) {
        NotificationSetting setting =
                findOrCreateSetting();

        setting.update(
                request.getBrowserNotificationEnabled(),
                request.getFallSensitivityThreshold(),
                request.getInactivityThresholdMinutes(),
                normalizeOptional(
                        request.getEmergencyContactName()
                ),
                normalizeOptional(
                        request.getEmergencyContactPhone()
                )
        );

        return NotificationSettingResponse.from(setting);
    }

    @Transactional
    public TestNotificationResponse createTestNotification(
            TestNotificationRequest request
    ) {
        Room room = roomRepository
                .findById(request.getRoomId())
                .orElseThrow(() ->
                        new CustomException(
                                RoomErrorCode.ROOM_NOT_FOUND
                        )
                );

        Event event = Event.create(
                room,
                EventType.FALL,
                OffsetDateTime.now(KOREA_ZONE),
                TEST_EVIDENCE_SUMMARY
        );

        Event savedEvent =
                eventRepository.save(event);

        return TestNotificationResponse.from(savedEvent);
    }

    private NotificationSetting findOrCreateSetting() {
        return notificationSettingRepository
                .findById(NotificationSetting.GLOBAL_SETTING_ID)
                .orElseGet(() ->
                        notificationSettingRepository.save(
                                NotificationSetting.createDefault()
                        )
                );
    }

    private String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}