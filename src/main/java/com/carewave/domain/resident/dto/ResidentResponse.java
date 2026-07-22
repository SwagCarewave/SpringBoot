package com.carewave.domain.resident.dto;

import com.carewave.domain.resident.entity.Resident;
import com.carewave.domain.resident.entity.ResidentGender;
import com.carewave.domain.resident.entity.ResidentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ResidentResponse {

    private Long residentId;
    private String name;
    private LocalDate birthDate;
    private ResidentGender gender;
    private String phoneNumber;
    private String guardianName;
    private String guardianPhoneNumber;
    private String note;

    private Long roomId;
    private String roomNumber;

    private ResidentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ResidentResponse from(Resident resident) {
        Long roomId = null;
        String roomNumber = null;

        if (resident.getRoom() != null) {
            roomId = resident.getRoom().getId();
            roomNumber = resident.getRoom().getRoomNumber();
        }

        return ResidentResponse.builder()
                .residentId(resident.getId())
                .name(resident.getName())
                .birthDate(resident.getBirthDate())
                .gender(resident.getGender())
                .phoneNumber(resident.getPhoneNumber())
                .guardianName(resident.getGuardianName())
                .guardianPhoneNumber(resident.getGuardianPhoneNumber())
                .note(resident.getNote())
                .roomId(roomId)
                .roomNumber(roomNumber)
                .status(resident.getStatus())
                .createdAt(resident.getCreatedAt())
                .updatedAt(resident.getUpdatedAt())
                .build();
    }
}