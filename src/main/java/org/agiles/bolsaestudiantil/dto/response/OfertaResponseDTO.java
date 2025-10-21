package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OfertaResponseDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String requisitos;
    private EmpresaSimpleDTO empresa;
    private String locacion;
    private String pagoAprox;
    private String modalidad;
    private String tipoContrato;
    private LocalDateTime publishDate;
    private LocalDateTime closeDate;
    private String status;
    private Set<AttributeDTO> attributes;
}
