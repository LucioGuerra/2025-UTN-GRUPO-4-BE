package org.agiles.bolsaestudiantil.service.viejo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.viejo.AplicanteFilterDTO;
import org.agiles.bolsaestudiantil.dto.request.viejo.ModAplicanteDTO;
import org.agiles.bolsaestudiantil.dto.response.viejo.AplicanteDTO;
import org.agiles.bolsaestudiantil.dto.response.viejo.AplicanteListaDTO;
import org.agiles.bolsaestudiantil.mapper.AplicanteMapper;
import org.agiles.bolsaestudiantil.repository.pre_refactor.AplicacionRepository;
import org.agiles.bolsaestudiantil.repository.pre_refactor.AplicanteRepository;
import org.agiles.bolsaestudiantil.repository.pre_refactor.OfertaRepository;
import org.agiles.bolsaestudiantil.specification.AplicanteSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AplicanteService {

    private final AplicanteRepository aplicanteRepository;
    private final AplicacionRepository aplicacionRepository;
    private final OfertaRepository ofertaRepository;
    private final AplicanteMapper aplicanteMapper;

    public AplicanteEntity findById(Long id) {
        return aplicanteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aplicante no encontrado con id: " + id));
    }

    public Page<AplicanteEntity> obtenerAplicantes(AplicanteFilterDTO filter, Pageable pageable) {
        return aplicanteRepository.findAll(
                AplicanteSpecification.conFiltros(filter),
                pageable
        );
    }

    public AplicanteDTO getAplicanteDTOById(Long id) {
        AplicanteEntity aplicante = findById(id);
        return aplicanteMapper.toDTO(aplicante);
    }
    
    @Transactional
    public AplicanteListaDTO getAplicantesPorOferta(Long ofertaId) {
        // Verificar que la oferta existe
        OfertaEntity oferta = ofertaRepository.findById(ofertaId)
                .orElseThrow(() -> new EntityNotFoundException("Oferta no encontrada con id: " + ofertaId));
        
        // Obtener todas las aplicaciones de esta oferta
        List<AplicacionEntity> aplicaciones = aplicacionRepository.findByOfertaId(ofertaId);
        
        // Mapear a DTOs incluyendo información de la aplicación
        List<AplicanteDTO> aplicanteDTOs = aplicaciones.stream()
                .map(aplicacion -> {
                    AplicanteDTO dto = aplicanteMapper.toDTO(aplicacion.getAplicante());
                    dto.setCartaPresentacion(aplicacion.getCartaPresentacion());
                    dto.setFechaAplicacion(aplicacion.getFechaAplicacion());
                    dto.setOfertaId(aplicacion.getOferta().getId());
                    return dto;
                })
                .collect(Collectors.toList());
        
        // Construir respuesta
        AplicanteListaDTO response = new AplicanteListaDTO();
        response.setOfertaId(ofertaId);
        response.setOfertaTitulo(oferta.getTitulo());
        response.setAplicantes(aplicanteDTOs);
        response.setTotalAplicantes(aplicanteDTOs.size());
        
        return response;
    }

    public Void actualizarAplicante(Long id, ModAplicanteDTO aplicanteDTO) {
        AplicanteEntity aplicante = findById(id);

        // Actualizar campos permitidos
        if (aplicanteDTO.getCarrera() != null) {
            aplicante.setCarrera(aplicanteDTO.getCarrera());
        }
        if (aplicanteDTO.getAnioIngreso() != null) {
            aplicante.setAnioIngreso(aplicanteDTO.getAnioIngreso());
        }
        if (aplicanteDTO.getCvUrl() != null) {
            aplicante.setCvUrl(aplicanteDTO.getCvUrl());
        }
        if (aplicanteDTO.getCvFileName() != null) {
            aplicante.setCvFileName(aplicanteDTO.getCvFileName());
        }

        aplicanteRepository.save(aplicante);
        return null;
    }
}
