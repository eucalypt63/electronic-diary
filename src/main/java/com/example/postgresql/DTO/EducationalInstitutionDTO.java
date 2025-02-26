package com.example.postgresql.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EducationalInstitutionDTO {
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private Long regionId;
    private Long settlementId;
}