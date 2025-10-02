package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.OfferRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.OfferResponseDTO;
import org.agiles.bolsaestudiantil.entity.*;
import org.agiles.bolsaestudiantil.exception.UnijobsException;
import org.agiles.bolsaestudiantil.mapper.OfferMapper;
import org.agiles.bolsaestudiantil.repository.AttributeRepository;
import org.agiles.bolsaestudiantil.repository.OfferRepository;
import org.agiles.bolsaestudiantil.repository.StudentOfferRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final StudentOfferRepository studentOfferRepository;
    private final StudentService studentService;
    private final AttributeRepository attributeRepository;
    private final OfferMapper offerMapper;

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
                    return attributeEntity;
                });
    }
}
