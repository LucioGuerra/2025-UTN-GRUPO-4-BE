package org.agiles.bolsaestudiantil.dto.request;

import lombok.Data;

@Data
public class CreateAssociationRequestDTO {
    private Long studentId;
    private String recognitionType;
}
