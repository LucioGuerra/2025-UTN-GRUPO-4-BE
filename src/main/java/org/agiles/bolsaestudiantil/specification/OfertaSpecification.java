package org.agiles.bolsaestudiantil.specification;

import jakarta.persistence.criteria.Predicate;
import org.agiles.bolsaestudiantil.dto.request.OfertaFilterDTO;
import org.agiles.bolsaestudiantil.entity.OfertaEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OfertaSpecification {

    public static Specification<OfertaEntity> withFilters(OfertaFilterDTO filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.getTitulo() != null && !filters.getTitulo().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("titulo")),
                        "%" + filters.getTitulo().toLowerCase() + "%"
                ));
            }

            if (filters.getEmpresaId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("empresa").get("id"), filters.getEmpresaId()));
            }

            if (filters.getTipoContrato() != null && !filters.getTipoContrato().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("tipoContrato"), filters.getTipoContrato()));
            }

            if (filters.getLocacion() != null && !filters.getLocacion().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("locacion")),
                        "%" + filters.getLocacion().toLowerCase() + "%"
                ));
            }

            if (filters.getStatus() != null && !filters.getStatus().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), filters.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
