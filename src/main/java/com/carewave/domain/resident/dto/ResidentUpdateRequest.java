package com.carewave.domain.resident.dto;

import com.carewave.domain.resident.entity.ResidentGender;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ResidentUpdateRequest {

    @Size(
            min = 1,
            max = 50,
            message = "Resident name must be between 1 and 50 characters."
    )
    private String name;

    @PastOrPresent(message = "Birth date cannot be in the future.")
    private LocalDate birthDate;

    private ResidentGender gender;

    @Size(max = 20, message = "Phone number must be 20 characters or fewer.")
    private String phoneNumber;

    @Size(
            min = 1,
            max = 50,
            message = "Guardian name must be between 1 and 50 characters."
    )
    private String guardianName;

    @Size(
            min = 1,
            max = 20,
            message = "Guardian phone number must be between 1 and 20 characters."
    )
    private String guardianPhoneNumber;

    @Size(max = 500, message = "Note must be 500 characters or fewer.")
    private String note;

    private Long roomId;
}