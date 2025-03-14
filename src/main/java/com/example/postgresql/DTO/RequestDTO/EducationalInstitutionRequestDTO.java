package com.example.postgresql.DTO.RequestDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EducationalInstitutionRequestDTO {
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private Long regionId;
    private Long settlementId;
}