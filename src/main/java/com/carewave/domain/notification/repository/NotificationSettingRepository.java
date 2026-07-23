package com.carewave.domain.notification.repository;

import com.carewave.domain.notification.entity.NotificationSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationSettingRepository
        extends JpaRepository<NotificationSetting, Long> {


}