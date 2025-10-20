package org.agiles.bolsaestudiantil.dto.request;

import lombok.Data;
import java.util.Set;

@Data
public class OfertaRequestDTO {
    private String titulo;
    private String descripcion;
    private String requisitos;
    private Long empresaId;
    private String locacion;
    private String pagoAprox;
    private String modalidad;
    private String tipoContrato;
    private Set<String> attributes;
}
