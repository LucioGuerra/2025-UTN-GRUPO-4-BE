package org.agiles.bolsaestudiantil.specification;

import jakarta.persistence.criteria.Predicate;
import org.agiles.bolsaestudiantil.dto.internal.ApplyFilter;
import org.agiles.bolsaestudiantil.entity.ApplyEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ApplySpecification {
    public static Specification<ApplyEntity> withFilters(ApplyFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getStudentId() != null) {
                predicates.add(cb.equal(root.get("student").get("id"), filter.getStudentId()));
            }

            if (filter.getOfferId() != null) {
                predicates.add(cb.equal(root.get("offer").get("id"), filter.getOfferId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}