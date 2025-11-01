package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;

@Data
public class SubjectResponseDTO {
    private Long id;
    private String name;
    private String code;
    private Integer note;
}