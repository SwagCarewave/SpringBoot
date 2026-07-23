package com.carewave.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnconfirmedEventCountResponse {

    private long count;
}