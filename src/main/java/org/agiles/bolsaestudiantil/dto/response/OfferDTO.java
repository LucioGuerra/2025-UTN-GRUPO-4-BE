package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class OfferDTO {
    private Long id;
    private Set<StudentDTO> students;
}
