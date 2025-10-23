package org.agiles.bolsaestudiantil.dto.request;

import lombok.Data;

@Data
public class ApplyRequestDTO {
    private Long offerId;
    private Long studentId;
    private String customCoverLetter;
}