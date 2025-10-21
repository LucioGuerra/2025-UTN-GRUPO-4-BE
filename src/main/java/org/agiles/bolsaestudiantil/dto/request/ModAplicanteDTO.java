package org.agiles.bolsaestudiantil.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ModAplicanteDTO {
    private String nombre;
    private String email;
    private String carrera;
    private Integer anioIngreso;
    private String cvUrl;
    private String cvFileName;
}
