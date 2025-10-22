package org.agiles.bolsaestudiantil.dto.response.viejo;

import lombok.Data;

@Data
public class EmpresaDTO {
    private Long id;
    private String nombre;
    private String logo;
    private String descripcion;
    private String sector;
    private String tamanio;
    private String sitioWeb;
}
