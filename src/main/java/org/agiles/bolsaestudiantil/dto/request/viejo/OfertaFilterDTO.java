package org.agiles.bolsaestudiantil.dto.request.viejo;

import lombok.Data;

@Data
public class OfertaFilterDTO {
    private String titulo;
    private Long empresaId;
    private String tipoContrato;
    private String locacion;
    private String status;
}
