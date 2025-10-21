package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;
import org.agiles.bolsaestudiantil.enums.EstadoAplicacion;

import java.util.List;

@Data
public class OfertaListaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String requisitos;
    private String modalidad; // "remoto" | "híbrido" | "presencial"
    private String locacion;
    private String pagoAprox;
    private List<String> atributos;
    private EstadoAplicacion estado;
    private EmpresaSimpleDTO empresa;
}
