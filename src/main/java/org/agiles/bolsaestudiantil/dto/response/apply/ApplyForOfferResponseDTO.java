package org.agiles.bolsaestudiantil.dto.response.apply;

import lombok.Data;
import org.agiles.bolsaestudiantil.dto.response.student.StudentSummaryResponseDTO;

@Data
public class ApplyForOfferResponseDTO {
    private Long id;
    private String customCoverLetter;
    private StudentSummaryResponseDTO student;
}