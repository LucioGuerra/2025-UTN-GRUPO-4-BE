package org.agiles.bolsaestudiantil.dto.request;

import lombok.Data;
import java.util.Set;

@Data
public class OfferRequestDTO {
    private String title;
    private String description;
    private String requirements;
    private String modality;
    private String location;
    private String approximatePayment;
    private Set<String> attributes;
}
