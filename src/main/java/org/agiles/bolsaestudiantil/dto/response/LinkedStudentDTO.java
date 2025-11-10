package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LinkedStudentDTO {
    private Long id;
    private Long studentId;
    private String name;
    private String surname;
    private String imageUrl;
    private String career;
    private Integer currentYearLevel;
    private String institution;
    private String recognitionType;
    private LocalDate associationDate;
}
