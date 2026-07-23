package com.carewave.domain.event.service;

import com.carewave.domain.event.dto.EventCreateRequest;
import com.carewave.domain.event.dto.EventPageResponse;
import com.carewave.domain.event.dto.EventResponse;
import com.carewave.domain.event.dto.UnconfirmedEventCountResponse;
import com.carewave.domain.event.entity.Event;
import com.carewave.domain.event.entity.EventStatus;
import com.carewave.domain.event.entity.EventType;
import com.carewave.domain.event.exception.EventErrorCode;
import com.carewave.domain.event.repository.EventRepository;
import com.carewave.domain.event.repository.EventSpecification;
import com.carewave.domain.room.entity.Room;
import com.carewave.domain.room.exception.RoomErrorCode;
import com.carewave.domain.room.repository.RoomRepository;
import com.carewave.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

    private static final ZoneId KOREA_ZONE =
            ZoneId.of("Asia/Seoul");

    private static final DateTimeFormatter
            CSV_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm:ss"
            );

    private final EventRepository eventRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public EventResponse createEvent(
            EventCreateRequest request
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
                request.getEventType(),
                request.getOccurredAt(),
                normalizeOptional(
                        request.getEvidenceSummary()
                )
        );

        Event savedEvent = eventRepository.save(event);

        return EventResponse.from(savedEvent);
    }

    public EventPageResponse getEvents(
            LocalDate startDate,
            LocalDate endDate,
            List<EventType> eventTypes,
            EventStatus status,
            int page,
            int size
    ) {
        validateDateRange(startDate, endDate);

        Specification<Event> specification =
                createSpecification(
                        startDate,
                        endDate,
                        eventTypes,
                        status
                );

        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Direction.DESC,
                        "occurredAt"
                )
        );

        Page<EventResponse> pageResult =
                eventRepository
                        .findAll(specification, pageable)
                        .map(EventResponse::from);

        return EventPageResponse.from(pageResult);
    }

    public EventResponse getEvent(Long eventId) {
        Event event = findEvent(eventId);

        return EventResponse.from(event);
    }

    public UnconfirmedEventCountResponse
    getUnconfirmedEventCount() {
        long count = eventRepository.countByStatus(
                EventStatus.UNCONFIRMED
        );

        return new UnconfirmedEventCountResponse(count);
    }

    @Transactional
    public EventResponse confirmEvent(Long eventId) {
        Event event = findEvent(eventId);

        if (event.getStatus() == EventStatus.CONFIRMED) {
            throw new CustomException(
                    EventErrorCode.EVENT_ALREADY_CONFIRMED
            );
        }

        event.confirm(
                OffsetDateTime.now(KOREA_ZONE)
        );

        return EventResponse.from(event);
    }

    public byte[] exportEvents(
            LocalDate startDate,
            LocalDate endDate,
            List<EventType> eventTypes,
            EventStatus status
    ) {
        validateDateRange(startDate, endDate);

        Specification<Event> specification =
                createSpecification(
                        startDate,
                        endDate,
                        eventTypes,
                        status
                );

        List<Event> events = eventRepository.findAll(
                specification,
                Sort.by(
                        Sort.Direction.DESC,
                        "occurredAt"
                )
        );

        StringBuilder csv = new StringBuilder();

        // Excel에서 한글이 깨지지 않도록 UTF-8 BOM 추가
        csv.append('\uFEFF');

        csv.append(
                "이벤트 ID,이벤트 유형,방 번호,"
        );
        csv.append(
                "발생 시각,확인 상태,판단 근거 요약,확인 시각\r\n"
        );

        for (Event event : events) {
            csv.append(event.getId())
                    .append(',');

            csv.append(
                    csvEscape(
                            event.getEventType()
                                    .getDisplayName()
                    )
            ).append(',');

            csv.append(
                    csvEscape(
                            event.getRoom()
                                    .getRoomNumber()
                    )
            ).append(',');

            csv.append(
                    csvEscape(
                            formatDateTime(
                                    event.getOccurredAt()
                            )
                    )
            ).append(',');

            String statusName =
                    event.getStatus()
                            == EventStatus.CONFIRMED
                            ? "확인 완료"
                            : "미확인";

            csv.append(csvEscape(statusName))
                    .append(',');

            csv.append(
                    csvEscape(
                            event.getEvidenceSummary()
                    )
            ).append(',');

            csv.append(
                    csvEscape(
                            formatDateTime(
                                    event.getConfirmedAt()
                            )
                    )
            );

            csv.append("\r\n");
        }

        return csv.toString()
                .getBytes(StandardCharsets.UTF_8);
    }

    private Event findEvent(Long eventId) {
        return eventRepository
                .findDetailById(eventId)
                .orElseThrow(() ->
                        new CustomException(
                                EventErrorCode.EVENT_NOT_FOUND
                        )
                );
    }

    private Specification<Event> createSpecification(
            LocalDate startDate,
            LocalDate endDate,
            List<EventType> eventTypes,
            EventStatus status
    ) {
        OffsetDateTime start =
                startDate == null
                        ? null
                        : startDate
                        .atStartOfDay(KOREA_ZONE)
                        .toOffsetDateTime();

        /*
         * 종료 날짜의 다음 날 00:00 미만으로 조회한다.
         * 예: endDate가 2026-07-23이면
         * 2026-07-24 00:00 전까지 포함된다.
         */
        OffsetDateTime endExclusive =
                endDate == null
                        ? null
                        : endDate
                        .plusDays(1)
                        .atStartOfDay(KOREA_ZONE)
                        .toOffsetDateTime();

        return Specification
                .where(
                        EventSpecification
                                .occurredAtGreaterThanOrEqualTo(
                                        start
                                )
                )
                .and(
                        EventSpecification
                                .occurredAtLessThan(
                                        endExclusive
                                )
                )
                .and(
                        EventSpecification
                                .eventTypeIn(eventTypes)
                )
                .and(
                        EventSpecification
                                .statusEquals(status)
                );
    }

    private void validateDateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (startDate != null
                && endDate != null
                && startDate.isAfter(endDate)) {
            throw new CustomException(
                    EventErrorCode.INVALID_DATE_RANGE
            );
        }
    }

    private String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    private String formatDateTime(
            OffsetDateTime dateTime
    ) {
        if (dateTime == null) {
            return "";
        }

        return dateTime
                .atZoneSameInstant(KOREA_ZONE)
                .format(CSV_DATE_TIME_FORMATTER);
    }

    private String csvEscape(String value) {
        if (value == null) {
            return "";
        }

        return "\""
                + value.replace("\"", "\"\"")
                + "\"";
    }
}