package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.repository.LanguageRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;
}
