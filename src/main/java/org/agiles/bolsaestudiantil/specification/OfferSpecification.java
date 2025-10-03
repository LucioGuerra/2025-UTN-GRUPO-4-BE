package org.agiles.bolsaestudiantil.specification;

import jakarta.persistence.criteria.Predicate;
import org.agiles.bolsaestudiantil.dto.request.OfferFilterDTO;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OfferSpecification {

    public static Specification<OfferEntity> withFilters(OfferFilterDTO filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.getTitle() != null && !filters.getTitle().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + filters.getTitle().toLowerCase() + "%"
                ));
            }

            if (filters.getCompany() != null && !filters.getCompany().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("company")),
                        "%" + filters.getCompany().toLowerCase() + "%"
                ));
            }

            if (filters.getContractType() != null && !filters.getContractType().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("contractType"), filters.getContractType()));
            }

            if (filters.getLocation() != null && !filters.getLocation().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("location")),
                        "%" + filters.getLocation().toLowerCase() + "%"
                ));
            }

            if (filters.getStatus() != null && !filters.getStatus().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), filters.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
