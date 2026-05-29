package com.carewave.domain.event.entity;

import com.carewave.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "events")
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예: 낙상 감지, 재실 감지, 공실 감지
    @Column(nullable = false, length = 50)
    private String eventType;

    // FastAPI에서 넘어온 실제 감지 시간
    @Column(nullable = false)
    private OffsetDateTime occurredAt;

    // 예: 미확인, 확인완료
    @Column(nullable = false, length = 20)
    private String status;

    public void updateStatus(String status) {
        this.status = status;
    }
}