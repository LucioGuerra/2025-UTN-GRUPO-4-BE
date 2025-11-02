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
                String capitalizedName = filter.getName().substring(0, 1).toUpperCase() + filter.getName().substring(1).toLowerCase();
                predicates.add(cb.like(root.get("name"), "%" + capitalizedName + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}