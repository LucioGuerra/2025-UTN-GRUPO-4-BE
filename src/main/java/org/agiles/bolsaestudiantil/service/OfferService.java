package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.OfferFilterDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferDTO;
import org.agiles.bolsaestudiantil.dto.response.PagedResponseDTO;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.entity.StudentOfferEntity;
import org.agiles.bolsaestudiantil.exception.UnijobsException;
import org.agiles.bolsaestudiantil.mapper.OfferMapper;
import org.agiles.bolsaestudiantil.repository.OfferRepository;
import org.agiles.bolsaestudiantil.repository.StudentOfferRepository;
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
    private final StudentOfferRepository studentOfferRepository;
    private final StudentService studentService;
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
    public void applyToOffer(Long offerId, Long studentId, String coverLetter) {
        StudentEntity student = studentService.findById(studentId);
        OfferEntity offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with id: " + offerId));

        this.validatePreviousApplication(offerId, studentId);

        offer.addStudent(student, coverLetter);
        offerRepository.save(offer);
    }


    private void validatePreviousApplication(Long offerId, Long studentId) {
        Optional<StudentOfferEntity> studentOfferOpt = studentOfferRepository.findByStudentAndOffer(studentId, offerId);
        if (studentOfferOpt.isPresent()) {
            throw new UnijobsException("STUDENT_ALREADY_APPLIED", "Student with id " + studentId + " has already applied to offer with id " + offerId);
        }
    }
}
