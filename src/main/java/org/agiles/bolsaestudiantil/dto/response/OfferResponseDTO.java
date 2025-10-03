package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;
import java.util.Set;

@Data
public class OfferResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String requirements;
    private String modality;
    private String location;
    private String approximatePayment;
    private Set<AttributeDTO> attributes;
}
