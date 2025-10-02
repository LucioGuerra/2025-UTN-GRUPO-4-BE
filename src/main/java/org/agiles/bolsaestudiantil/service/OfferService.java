package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.OfferDTO;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.mapper.OfferMapper;
import org.agiles.bolsaestudiantil.repository.OfferRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final StudentService studentService;

    public OfferDTO applyToOffer(Long offerId, Long studentId) {
        StudentEntity student = studentService.findById(studentId);
        OfferEntity offer = offerRepository.findById(offerId).orElseThrow(() -> new EntityNotFoundException("Offer not found with id: " + offerId));

        offer.addStudent(student);

        offerRepository.save(offer);

        OfferDTO dto = offerMapper.toDTO(offer);

        return dto;
    }
}
