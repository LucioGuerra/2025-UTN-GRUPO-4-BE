package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkExperienceResponseDTO {
    private Long id;
    private String company;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Boolean isCurrentJob;
}
