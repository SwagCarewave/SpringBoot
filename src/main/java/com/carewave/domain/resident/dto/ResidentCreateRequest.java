package com.carewave.domain.resident.dto;

import com.carewave.domain.resident.entity.ResidentGender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ResidentCreateRequest {

    @NotBlank(message = "Resident name is required.")
    @Size(max = 50, message = "Resident name must be 50 characters or fewer.")
    private String name;

    @NotNull(message = "Birth date is required.")
    @PastOrPresent(message = "Birth date cannot be in the future.")
    private LocalDate birthDate;

    @NotNull(message = "Gender is required.")
    private ResidentGender gender;

    @Size(max = 20, message = "Phone number must be 20 characters or fewer.")
    private String phoneNumber;

    @NotBlank(message = "Guardian name is required.")
    @Size(max = 50, message = "Guardian name must be 50 characters or fewer.")
    private String guardianName;

    @NotBlank(message = "Guardian phone number is required.")
    @Size(max = 20, message = "Guardian phone number must be 20 characters or fewer.")
    private String guardianPhoneNumber;

    @Size(max = 500, message = "Note must be 500 characters or fewer.")
    private String note;

    private Long roomId;
}