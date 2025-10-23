package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;
import org.agiles.bolsaestudiantil.dto.response.apply.ApplyForOfferResponseDTO;

import java.util.List;

@Data
public class OfferResponseDTO {
    private Long id;

    private String title;

    private String description;

    private String requirements;

    private String modality;

    private String location;

    private String estimatedPayment;

    private List<ApplyForOfferResponseDTO> applyList;

    private UserResponseDTO bidder;


}
