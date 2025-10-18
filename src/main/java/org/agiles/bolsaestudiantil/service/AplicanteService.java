package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.entity.AplicanteEntity;
import org.agiles.bolsaestudiantil.repository.AplicanteRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AplicanteService {

    private final AplicanteRepository aplicanteRepository;

    public AplicanteEntity findById(Long id) {
        return aplicanteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aplicante no encontrado con id: " + id));
    }
}
