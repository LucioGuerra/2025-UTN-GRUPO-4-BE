package org.agiles.bolsaestudiantil.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class OfferRequestDTO {
    private String title;
    private String description;
    private String company;
    private String location;
    private BigDecimal salary;
    private String workType;
    private String contractType;
    private Set<String> attributes;
}
