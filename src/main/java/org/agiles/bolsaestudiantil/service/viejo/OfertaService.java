package org.agiles.bolsaestudiantil.service.viejo;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.OfertaFilterDTO;
import org.agiles.bolsaestudiantil.dto.request.OfertaRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.OfertaListaDTO;
import org.agiles.bolsaestudiantil.dto.response.OfertaResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.PagedResponseDTO;
import org.agiles.bolsaestudiantil.entity.*;
import org.agiles.bolsaestudiantil.enums.EstadoAplicacion;
import org.agiles.bolsaestudiantil.exception.UnijobsException;
import org.agiles.bolsaestudiantil.mapper.OfertaMapper;
import org.agiles.bolsaestudiantil.repository.pre_refactor.AttributeRepository;
import org.agiles.bolsaestudiantil.repository.pre_refactor.OfertaRepository;
import org.agiles.bolsaestudiantil.repository.pre_refactor.AplicacionRepository;
import org.agiles.bolsaestudiantil.specification.OfertaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfertaService {

    private final OfertaRepository ofertaRepository;
    private final AplicacionRepository aplicacionRepository;
    private final AplicanteService aplicanteService;
    private final EmpresaService empresaService;
    private final AttributeRepository attributeRepository;
    private final OfertaMapper ofertaMapper;

    @Transactional
    public PagedResponseDTO<OfertaListaDTO> getOfertas(OfertaFilterDTO filters, int page, int size, Long usuarioId) {
        // Validar tamaño de página
        if (size != 5 && size != 10 && size != 20) {
            size = 10; // valor por defecto
        }

        // Crear Pageable con ordenamiento por fecha de publicación (más recientes primero)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "publishDate"));

        // Crear especificación de filtros
        Specification<OfertaEntity> spec = OfertaSpecification.withFilters(filters);

        // Ejecutar consulta
        Page<OfertaEntity> ofertaPage = ofertaRepository.findAll(spec, pageable);

        // Mapear entidades a DTOs y agregar atributos y estado
        List<OfertaListaDTO> ofertaDTOs = ofertaPage.getContent().stream()
                .map(oferta -> {
                    OfertaListaDTO dto = ofertaMapper.toDTO(oferta);
                    
                    // Convertir atributos a lista de strings
                    List<String> atributos = oferta.getAttributes().stream()
                            .map(attr -> attr.getAttribute().getName())
                            .collect(Collectors.toList());
                    dto.setAtributos(atributos);
                    
                    // Calcular estado según usuarioId
                    EstadoAplicacion estado = calcularEstadoAplicacion(oferta.getId(), usuarioId);
                    dto.setEstado(estado);
                    
                    return dto;
                })
                .collect(Collectors.toList());

        // Construir respuesta paginada
        return new PagedResponseDTO<>(
                ofertaDTOs,
                ofertaPage.getNumber(),
                ofertaPage.getSize(),
                ofertaPage.getTotalElements(),
                ofertaPage.getTotalPages(),
                ofertaPage.isFirst(),
                ofertaPage.isLast()
        );
    }

    private EstadoAplicacion calcularEstadoAplicacion(Long ofertaId, Long usuarioId) {
        if (usuarioId == null) {
            return EstadoAplicacion.NO_APLICADO;
        }
        
        Optional<AplicacionEntity> aplicacion = aplicacionRepository.findByAplicanteAndOferta(usuarioId, ofertaId);
        return aplicacion.isPresent() ? EstadoAplicacion.APLICADO : EstadoAplicacion.NO_APLICADO;
    }

    @Transactional
    public void aplicarAOferta(Long ofertaId, Long usuarioId, String cartaPresentacion) {
        AplicanteEntity aplicante = aplicanteService.findById(usuarioId);
        OfertaEntity oferta = ofertaRepository.findById(ofertaId)
                .orElseThrow(() -> new EntityNotFoundException("Oferta no encontrada con id: " + ofertaId));

        this.validatePreviousApplication(ofertaId, usuarioId);

        oferta.addAplicante(aplicante, cartaPresentacion);
        ofertaRepository.save(oferta);
    }


    private void validatePreviousApplication(Long ofertaId, Long usuarioId) {
        Optional<AplicacionEntity> aplicacionOpt = aplicacionRepository.findByAplicanteAndOferta(usuarioId, ofertaId);
        if (aplicacionOpt.isPresent()) {
            throw new UnijobsException("APLICANTE_YA_APLICADO", "Aplicante con id " + usuarioId + " ya ha aplicado a la oferta con id " + ofertaId);
        }
    }

    @Transactional
    public OfertaResponseDTO createOferta(OfertaRequestDTO requestDTO) {
        OfertaEntity ofertaEntity = ofertaMapper.createOfertaRequestToEntity(requestDTO);

        // Asignar empresa
        if (requestDTO.getEmpresaId() != null) {
            EmpresaEntity empresa = empresaService.getEntityById(requestDTO.getEmpresaId());
            ofertaEntity.setEmpresa(empresa);
        }

        // Asignar atributos
        if (requestDTO.getAttributes() != null && !requestDTO.getAttributes().isEmpty()) {
            for (String attribute : requestDTO.getAttributes()) {
                AttributeEntity attributeEntity = findOrCreateAttribute(attribute);

                OfertaAttributeEntity ofertaAttribute = new OfertaAttributeEntity();
                ofertaAttribute.setOferta(ofertaEntity);
                ofertaAttribute.setAttribute(attributeEntity);

                ofertaEntity.getAttributes().add(ofertaAttribute);
            }
        }

        OfertaEntity savedOferta = ofertaRepository.save(ofertaEntity);
        return ofertaMapper.toOfertaResponseDTO(savedOferta);
    }

    private AttributeEntity findOrCreateAttribute(String name) {
        return attributeRepository.findByName(name)
                .orElseGet(() -> {
                    AttributeEntity attributeEntity = new AttributeEntity();
                    attributeEntity.setName(name);
                    return attributeRepository.save(attributeEntity);
                });
    }

    public OfertaEntity getEntityById(Long id) {
        return ofertaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Oferta no encontrada con id: " + id));
    }
}
