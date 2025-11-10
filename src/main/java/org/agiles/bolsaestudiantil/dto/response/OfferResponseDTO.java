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

    private List<String> attributes;

    private UserResponseDTO bidder;

    private Integer positiveVotes;

    private Integer negativeVotes;

    private Integer totalScore;

    private Boolean userVote; // null = no votó, true = like, false = dislike


}
