package org.agiles.bolsaestudiantil.dto.response.offer;

import lombok.Data;

@Data
public class OfferSummaryResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String modality;
    private String location;
    private String estimatedPayment;
}