package org.agiles.bolsaestudiantil.dto.internal;

import lombok.Data;

@Data
public class OfferFilter {
    private String title;
    private String modality;
    private String location;
    private String estimatedPayment;
    private String description;
    private String requirements;
    private Long bidderId;
}
