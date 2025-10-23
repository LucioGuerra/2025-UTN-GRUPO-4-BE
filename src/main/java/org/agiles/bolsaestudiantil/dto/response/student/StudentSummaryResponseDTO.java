package org.agiles.bolsaestudiantil.dto.response.student;

import lombok.Data;

@Data
public class StudentSummaryResponseDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String career;
    private String institution;
    private String role = "Student";
}