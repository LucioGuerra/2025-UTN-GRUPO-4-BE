package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;
import org.agiles.bolsaestudiantil.dto.response.offer.OfferSummaryResponseDTO;

@Data
public class ApplyForStudentResponseDTO {
    private Long id;
    private String customCoverLetter;
    private OfferSummaryResponseDTO offer;
}