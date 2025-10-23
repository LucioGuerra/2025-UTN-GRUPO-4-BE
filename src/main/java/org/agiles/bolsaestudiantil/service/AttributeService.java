package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.entity.AttributeEntity;
import org.agiles.bolsaestudiantil.repository.AttributeRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttributeService {

    private AttributeRepository attributeRepository;

    public AttributeEntity findOrCreateAttribute(String name) {
        return attributeRepository.findByName(name)
                .orElseGet(() -> {
                    AttributeEntity attributeEntity = new AttributeEntity();
                    attributeEntity.setName(name);
                    return attributeRepository.save(attributeEntity);
                });
    }


}
