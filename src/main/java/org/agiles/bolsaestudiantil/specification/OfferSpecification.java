package org.agiles.bolsaestudiantil.specification;

import jakarta.persistence.criteria.Predicate;
import org.agiles.bolsaestudiantil.dto.internal.OfferFilter;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OfferSpecification {
    public static Specification<OfferEntity> withFilters(OfferFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getTitle() != null && !filter.getTitle().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + filter.getTitle().toLowerCase() + "%"));
            }

            if (filter.getDescription() != null && !filter.getDescription().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + filter.getDescription().toLowerCase() + "%"));
            }

            if (filter.getRequirements() != null && !filter.getRequirements().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("requirements")), "%" + filter.getRequirements().toLowerCase() + "%"));
            }

            if (filter.getModality() != null && !filter.getModality().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("modality")), filter.getModality().toLowerCase()));
            }

            if (filter.getLocation() != null && !filter.getLocation().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("location")), "%" + filter.getLocation().toLowerCase() + "%"));
            }

            if (filter.getEstimatedPayment() != null && !filter.getEstimatedPayment().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("estimatedPayment")), "%" + filter.getEstimatedPayment().toLowerCase() + "%"));
            }

            if (filter.getBidderId() != null) {
                predicates.add(cb.equal(root.get("bidder").get("id"), filter.getBidderId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
