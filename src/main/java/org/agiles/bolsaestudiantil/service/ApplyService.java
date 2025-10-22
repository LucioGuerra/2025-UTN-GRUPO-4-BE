package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.repository.ApplyRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;


    public void createApply() {
        // Lógica para crear una aplicación
    }
}
