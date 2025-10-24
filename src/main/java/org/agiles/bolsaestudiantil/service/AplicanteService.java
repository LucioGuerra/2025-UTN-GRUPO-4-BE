package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.AplicanteFilterDTO;
import org.agiles.bolsaestudiantil.dto.request.MateriaDTO;
import org.agiles.bolsaestudiantil.dto.request.ModAplicanteDTO;
import org.agiles.bolsaestudiantil.dto.response.AplicanteDTO;
import org.agiles.bolsaestudiantil.dto.response.AplicanteListaDTO;
import org.agiles.bolsaestudiantil.entity.*;
import org.agiles.bolsaestudiantil.mapper.AplicanteMapper;
import org.agiles.bolsaestudiantil.mapper.MateriaMapper;
import org.agiles.bolsaestudiantil.repository.*;
import org.agiles.bolsaestudiantil.specification.AplicanteSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AplicanteService {

    private final AplicanteRepository aplicanteRepository;
    private final AplicacionRepository aplicacionRepository;
    private final OfertaRepository ofertaRepository;
    private final AplicanteMapper aplicanteMapper;

    private final MateriaRepository materiaRepository;
    private final MateriaXAplicanteRepository materiaXAplicanteRepository;
    private final MateriaMapper materiaMapper;

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

    @Transactional
    public List<MateriaDTO> cargarMaterias(Long id, List<MateriaDTO> materias){
        AplicanteEntity aplicante = findById(id);

        aplicante.getMaterias().clear();

        Set<MateriaXAplicanteEntity> nuevasMaterias = new HashSet<>();

        for (MateriaDTO dto : materias) {
            if (dto.getNota() != null && dto.getNota() >= 6) {
                MateriaEntity materia = materiaRepository.findByCodigo(dto.getCodigo())
                        .orElseGet(() -> {
                            MateriaEntity nuevaMateria = new MateriaEntity();
                            nuevaMateria.setCodigo(dto.getCodigo());
                            nuevaMateria.setNombre(dto.getNombre());
                            return materiaRepository.save(nuevaMateria);
                        });

                MateriaXAplicanteEntity link = new MateriaXAplicanteEntity();
                link.setAplicante(aplicante);
                link.setMateria(materia);
                link.setNota(dto.getNota());

                nuevasMaterias.add(link);
            }
        }

        aplicante.getMaterias().addAll(nuevasMaterias);
        AplicanteEntity aplicanteGuardado = aplicanteRepository.save(aplicante);

        return aplicanteGuardado.getMaterias().stream()
                .map(materiaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
