package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;

@Data
public class ApplyResponseDTO {
    private StudentResponseDTO student;
    private String customCoverLetter;
}
