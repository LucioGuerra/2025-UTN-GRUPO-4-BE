package org.agiles.bolsaestudiantil.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkExperienceRequestDTO {
    private Long studentId;
    private String company;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Boolean isCurrentJob;
}
