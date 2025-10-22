package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.entity.ApplyEntity;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.repository.ApplyRepository;
import org.agiles.bolsaestudiantil.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final StudentService studentService;
    private final OfferService offerService;


    public ApplyEntity createApply(Long offerId, Long studentId, String customCoverLetter) {
        ApplyEntity apply = new ApplyEntity();

        OfferEntity offer = offerService.getOfferEntityById(offerId);
        apply.setOffer(offer);

        StudentEntity student = studentService.getStudentEntityById(studentId);
        apply.setStudent(student);

        if (customCoverLetter != null) {
            apply.setCustomCoverLetter(customCoverLetter);
        }

        return applyRepository.save(apply);
    }
}
