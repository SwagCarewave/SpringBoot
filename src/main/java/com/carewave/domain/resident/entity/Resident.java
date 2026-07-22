package com.carewave.domain.resident.entity;

import com.carewave.common.BaseEntity;
import com.carewave.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "residents",
        indexes = {
                @Index(
                        name = "idx_resident_status",
                        columnList = "status"
                ),
                @Index(
                        name = "idx_resident_room_id",
                        columnList = "room_id"
                )
        }
)
public class Resident extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ResidentGender gender;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "guardian_name", nullable = false, length = 50)
    private String guardianName;

    @Column(name = "guardian_phone_number", nullable = false, length = 20)
    private String guardianPhoneNumber;

    @Column(length = 500)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ResidentStatus status;

    private Resident(
            String name,
            LocalDate birthDate,
            ResidentGender gender,
            String phoneNumber,
            String guardianName,
            String guardianPhoneNumber,
            String note,
            Room room,
            ResidentStatus status
    ) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.guardianName = guardianName;
        this.guardianPhoneNumber = guardianPhoneNumber;
        this.note = note;
        this.room = room;
        this.status = status;
    }

    public static Resident create(
            String name,
            LocalDate birthDate,
            ResidentGender gender,
            String phoneNumber,
            String guardianName,
            String guardianPhoneNumber,
            String note,
            Room room
    ) {
        return new Resident(
                name,
                birthDate,
                gender,
                phoneNumber,
                guardianName,
                guardianPhoneNumber,
                note,
                room,
                ResidentStatus.ACTIVE
        );
    }

    public void update(
            String name,
            LocalDate birthDate,
            ResidentGender gender,
            String phoneNumber,
            String guardianName,
            String guardianPhoneNumber,
            String note,
            Room room
    ) {
        if (name != null) {
            this.name = name;
        }

        if (birthDate != null) {
            this.birthDate = birthDate;
        }

        if (gender != null) {
            this.gender = gender;
        }

        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }

        if (guardianName != null) {
            this.guardianName = guardianName;
        }

        if (guardianPhoneNumber != null) {
            this.guardianPhoneNumber = guardianPhoneNumber;
        }

        if (note != null) {
            this.note = note;
        }

        if (room != null) {
            this.room = room;
        }
    }

    public void updateStatus(ResidentStatus status) {
        this.status = status;
    }
}