package org.agiles.bolsaestudiantil.specification;

import jakarta.persistence.criteria.Predicate;
import org.agiles.bolsaestudiantil.dto.request.viejo.AplicanteFilterDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AplicanteSpecification {
    public static Specification<AplicanteEntity> conFiltros(AplicanteFilterDTO filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getNombre() != null && !filter.getNombre().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("nombre")),
                        "%" + filter.getNombre().toLowerCase() + "%"
                ));
            }

            if (filter.getEmail() != null && !filter.getEmail().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("email")),
                        "%" + filter.getEmail().toLowerCase() + "%"
                ));
            }

            if (filter.getCarrera() != null && !filter.getCarrera().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("carrera")),
                        "%" + filter.getCarrera().toLowerCase() + "%"
                ));
            }

            if (filter.getAnioIngresoDesde() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("anioIngreso"),
                        filter.getAnioIngresoDesde()
                ));
            }

            if (filter.getAnioIngresoHasta() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("anioIngreso"),
                        filter.getAnioIngresoHasta()
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
