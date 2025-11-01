package org.agiles.bolsaestudiantil.dto.request;

import lombok.Data;

@Data
public class SubjectRequestDTO {
    private String name;
    private String code;
    private Integer note;
}