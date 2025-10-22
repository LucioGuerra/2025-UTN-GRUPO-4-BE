package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.repository.AttributeRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttributeService {

    private AttributeRepository attributeRepository;
}
