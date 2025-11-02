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
        String capitalizedName = capitalizeFirstLetter(name);
        return attributeRepository.findByName(capitalizedName)
                .orElseGet(() -> {
                    AttributeEntity attributeEntity = new AttributeEntity();
                    attributeEntity.setName(capitalizedName);
                    return attributeRepository.save(attributeEntity);
                });
    }

    public List<AttributeResponseDTO> getAllAttributes(AttributeFilter filter) {
        List<AttributeEntity> attributes = attributeRepository.findAll(AttributeSpecification.withFilters(filter));
        return attributes.stream().map(attributeMapper::toResponseDTO).toList();
    }

    private String capitalizeFirstLetter(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
