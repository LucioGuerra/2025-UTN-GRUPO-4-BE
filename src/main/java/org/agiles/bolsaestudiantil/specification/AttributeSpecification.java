package org.agiles.bolsaestudiantil.specification;

import jakarta.persistence.criteria.Predicate;
import org.agiles.bolsaestudiantil.dto.internal.AttributeFilter;
import org.agiles.bolsaestudiantil.entity.AttributeEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AttributeSpecification {
    public static Specification<AttributeEntity> withFilters(AttributeFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isBlank()) {
                predicates.add(cb.like(root.get("name"), "%" + filter.getName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}