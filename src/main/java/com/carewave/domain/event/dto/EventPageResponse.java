package com.carewave.domain.event.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class EventPageResponse {

    private List<EventResponse> events;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean first;

    private boolean last;

    public static EventPageResponse from(
            Page<EventResponse> pageResult
    ) {
        return EventPageResponse.builder()
                .events(pageResult.getContent())
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .first(pageResult.isFirst())
                .last(pageResult.isLast())
                .build();
    }
}