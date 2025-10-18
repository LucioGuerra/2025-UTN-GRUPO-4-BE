package org.agiles.bolsaestudiantil.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.OfferFilterDTO;
import org.agiles.bolsaestudiantil.dto.request.OfferRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.PagedResponseDTO;
import org.agiles.bolsaestudiantil.entity.*;
import org.agiles.bolsaestudiantil.exception.UnijobsException;
import org.agiles.bolsaestudiantil.mapper.OfferMapper;
import org.agiles.bolsaestudiantil.repository.AttributeRepository;
import org.agiles.bolsaestudiantil.repository.OfferRepository;
import org.agiles.bolsaestudiantil.repository.AplicacionRepository;
import org.agiles.bolsaestudiantil.specification.OfferSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final AplicacionRepository aplicacionRepository;
    private final AplicanteService aplicanteService;
    private final AttributeRepository attributeRepository;
    private final OfferMapper offerMapper;

    public PagedResponseDTO<OfferDTO> getOffers(OfferFilterDTO filters, int page, int size) {
        // Validar tamaño de página
        if (size != 5 && size != 10 && size != 20) {
            size = 10; // valor por defecto
        }

        // Crear Pageable con ordenamiento por fecha de publicación (más recientes primero)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "publishDate"));

        // Crear especificación de filtros
        Specification<OfferEntity> spec = OfferSpecification.withFilters(filters);

        // Ejecutar consulta
        Page<OfferEntity> offerPage = offerRepository.findAll(spec, pageable);

        // Mapear entidades a DTOs
        List<OfferDTO> offerDTOs = offerPage.getContent().stream()
                .map(offerMapper::toDTO)
                .collect(Collectors.toList());

        // Construir respuesta paginada
        return new PagedResponseDTO<>(
                offerDTOs,
                offerPage.getNumber(),
                offerPage.getSize(),
                offerPage.getTotalElements(),
                offerPage.getTotalPages(),
                offerPage.isFirst(),
                offerPage.isLast()
        );
    }

    @Transactional
    public void applyToOffer(Long offerId, Long aplicanteId, String cartaPresentacion) {
        AplicanteEntity aplicante = aplicanteService.findById(aplicanteId);
        OfferEntity offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException("Oferta no encontrada con id: " + offerId));

        this.validatePreviousApplication(offerId, aplicanteId);

        offer.addAplicante(aplicante, cartaPresentacion);
        offerRepository.save(offer);
    }


    private void validatePreviousApplication(Long offerId, Long aplicanteId) {
        Optional<AplicacionEntity> aplicacionOpt = aplicacionRepository.findByAplicanteAndOferta(aplicanteId, offerId);
        if (aplicacionOpt.isPresent()) {
            throw new UnijobsException("APLICANTE_YA_APLICADO", "Aplicante con id " + aplicanteId + " ya ha aplicado a la oferta con id " + offerId);
        }
    }

    @Transactional
    public OfferResponseDTO createOffer(OfferRequestDTO requestDTO) {
        OfferEntity offerEntity = offerMapper.createOfferRequestToEntity(requestDTO);

        if (requestDTO.getAttributes() != null && !requestDTO.getAttributes().isEmpty()) {
            for (String attribute : requestDTO.getAttributes()) {
                AttributeEntity attributeEntity = findOrCreateAttribute(attribute);

                OfferAttributeEntity offerAttribute = new OfferAttributeEntity();
                offerAttribute.setOffer(offerEntity);
                offerAttribute.setAttribute(attributeEntity);

                offerEntity.getAttributes().add(offerAttribute);
            }
        }

        OfferEntity savedOffer = offerRepository.save(offerEntity);
        return offerMapper.toOfferResponseDTO(savedOffer);
    }

    private AttributeEntity findOrCreateAttribute(String name) {
        return attributeRepository.findByName(name)
                .orElseGet(() -> {
                    AttributeEntity attributeEntity = new AttributeEntity();
                    attributeEntity.setName(name);
                    return attributeRepository.save(attributeEntity);
                });
    }
}
