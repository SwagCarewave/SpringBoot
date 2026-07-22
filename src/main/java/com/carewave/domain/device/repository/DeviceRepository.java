package com.carewave.domain.device.repository;

import com.carewave.domain.device.entity.Device;
import com.carewave.domain.device.entity.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    boolean existsByDeviceCode(String deviceCode);

    boolean existsByDeviceCodeAndIdNot(
            String deviceCode,
            Long deviceId
    );

    List<Device> findAllByStatusOrderByDeviceCodeAsc(
            DeviceStatus status
    );

    List<Device> findAllByOrderByDeviceCodeAsc();

    List<Device> findAllByRoomIdOrderByDeviceCodeAsc(
            Long roomId
    );

    long countByRoomId(Long roomId);
}