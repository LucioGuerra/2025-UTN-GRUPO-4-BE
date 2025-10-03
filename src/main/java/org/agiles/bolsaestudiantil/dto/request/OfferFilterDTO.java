package org.agiles.bolsaestudiantil.dto.request;

import lombok.Data;

@Data
public class OfferFilterDTO {
    private String title;
    private String company;
    private String contractType;
    private String location;
    private String status;
}
