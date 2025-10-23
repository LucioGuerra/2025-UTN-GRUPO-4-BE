package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.entity.LanguageEntity;
import org.agiles.bolsaestudiantil.repository.LanguageRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;


    public LanguageEntity createLanguage(String name, Integer level) {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setName(name);
        languageEntity.setLevel(level);
        return languageRepository.save(languageEntity);
    }

    public void deleteLanguage(Long id) {
        languageRepository.deleteById(id);
    }
}
