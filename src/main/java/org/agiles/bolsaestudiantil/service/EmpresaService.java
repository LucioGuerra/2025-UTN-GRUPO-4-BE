package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.EmpresaDTO;
import org.agiles.bolsaestudiantil.dto.response.EmpresaSimpleDTO;
import org.agiles.bolsaestudiantil.mapper.EmpresaMapper;
import org.agiles.bolsaestudiantil.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;

    public List<EmpresaDTO> findAll() {
        return empresaRepository.findAll().stream()
                .map(empresaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EmpresaDTO findById(Long id) {
        EmpresaEntity empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada con id: " + id));
        return empresaMapper.toDTO(empresa);
    }

    public EmpresaDTO create(EmpresaDTO empresaDTO) {
        EmpresaEntity empresa = empresaMapper.toEntity(empresaDTO);
        EmpresaEntity savedEmpresa = empresaRepository.save(empresa);
        return empresaMapper.toDTO(savedEmpresa);
    }

    public EmpresaDTO update(Long id, EmpresaDTO empresaDTO) {
        EmpresaEntity empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada con id: " + id));
        
        empresa.setNombre(empresaDTO.getNombre());
        empresa.setLogo(empresaDTO.getLogo());
        empresa.setDescripcion(empresaDTO.getDescripcion());
        empresa.setSector(empresaDTO.getSector());
        empresa.setTamanio(empresaDTO.getTamanio());
        empresa.setSitioWeb(empresaDTO.getSitioWeb());
        
        EmpresaEntity updatedEmpresa = empresaRepository.save(empresa);
        return empresaMapper.toDTO(updatedEmpresa);
    }

    public void delete(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new EntityNotFoundException("Empresa no encontrada con id: " + id);
        }
        empresaRepository.deleteById(id);
    }

    public EmpresaEntity getEntityById(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada con id: " + id));
    }

    public List<EmpresaSimpleDTO> searchByNombre(String nombre) {
        return empresaRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(empresaMapper::toSimpleDTO)
                .collect(Collectors.toList());
    }
}
