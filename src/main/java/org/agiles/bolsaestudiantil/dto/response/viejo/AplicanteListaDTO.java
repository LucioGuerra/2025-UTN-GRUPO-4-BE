package org.agiles.bolsaestudiantil.dto.response.viejo;

import lombok.Data;

import java.util.List;

@Data
public class AplicanteListaDTO {
    private Long ofertaId;
    private String ofertaTitulo;
    private List<AplicanteDTO> aplicantes;
    private Integer totalAplicantes;
}
