package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.internal.AttributeFilter;
import org.agiles.bolsaestudiantil.dto.response.AttributeResponseDTO;
import org.agiles.bolsaestudiantil.entity.AttributeEntity;
import org.agiles.bolsaestudiantil.mapper.AttributeMapper;
import org.agiles.bolsaestudiantil.repository.AttributeRepository;
import org.agiles.bolsaestudiantil.specification.AttributeSpecification;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttributeService {

    private final AttributeRepository attributeRepository;
    private final AttributeMapper attributeMapper;

    public AttributeEntity findOrCreateAttribute(String name) {
        return attributeRepository.findByName(name)
                .orElseGet(() -> {
                    AttributeEntity attributeEntity = new AttributeEntity();
                    attributeEntity.setName(name);
                    return attributeRepository.save(attributeEntity);
                });
    }

    public List<AttributeResponseDTO> getAllAttributes(AttributeFilter filter) {
        List<AttributeEntity> attributes = attributeRepository.findAll(AttributeSpecification.withFilters(filter));
        return attributes.stream().map(attributeMapper::toResponseDTO).toList();
    }
}
