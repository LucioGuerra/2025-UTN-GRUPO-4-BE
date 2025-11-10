package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;

@Data
public class OfferVoteResponseDTO {
    private Boolean userVote; // null = no votó, true = like, false = dislike
    private Integer positiveVotes;
    private Integer negativeVotes;
    private Integer totalScore;
}
