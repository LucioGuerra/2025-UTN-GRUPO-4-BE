package org.agiles.bolsaestudiantil.dto.request.update;

import lombok.Data;

import java.util.List;

@Data
public class OfferUpdateRequestDTO {
    private String title;
    private String description;
    private String requirements;
    private String modality;
    private String location;
    private String estimatedPayment;
    private List<String> attributes;
}