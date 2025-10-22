package org.agiles.bolsaestudiantil.dto.response.viejo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AplicanteDTO {
    private Long id;
    private Long usuarioId; // mismo que id
    private String nombre;
    private String email;
    private String carrera;
    private Integer anioIngreso;
    private String cvUrl;
    private String cvFileName;
    private String cartaPresentacion;
    private LocalDateTime fechaAplicacion;
    private Long ofertaId;
}
