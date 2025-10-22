package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.repository.SubjectRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
}
