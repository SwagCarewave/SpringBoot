package com.carewave.domain.event.service;

import com.carewave.domain.event.dto.EventCreateRequest;
import com.carewave.domain.event.dto.EventResponse;
import com.carewave.domain.event.entity.Event;
import com.carewave.domain.event.repository.EventRepository;
import com.carewave.global.error.CustomException;
import com.carewave.global.error.GeneralErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;

    @Transactional
    public EventResponse createEvent(EventCreateRequest request) {
        Event event = Event.builder()
                .eventType(request.getEventType())
                .occurredAt(request.getOccurredAt())
                .status(request.getStatus())
                .build();

        Event savedEvent = eventRepository.save(event);

        return EventResponse.from(savedEvent);
    }

    public List<EventResponse> getEvents() {
        return eventRepository.findAll()
                .stream()
                .map(EventResponse::from)
                .toList();
    }

    public EventResponse getEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(GeneralErrorCode.NOT_FOUND));

        return EventResponse.from(event);
    }

    @Transactional
    public EventResponse updateEventStatus(Long eventId, String status) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(GeneralErrorCode.NOT_FOUND));

        event.updateStatus(status);

        return EventResponse.from(event);
    }
}