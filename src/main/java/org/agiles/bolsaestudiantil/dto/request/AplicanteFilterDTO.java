package org.agiles.bolsaestudiantil.dto.request;

import lombok.Data;

@Data
public class AplicanteFilterDTO {
    private String nombre;
    private String email;
    private String carrera;
    private Integer anioIngresoDesde;
    private Integer anioIngresoHasta;
}
