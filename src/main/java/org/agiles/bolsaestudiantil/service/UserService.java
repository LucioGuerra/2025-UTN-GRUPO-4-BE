package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.UserResponseDTO;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.entity.UserEntity;
import org.agiles.bolsaestudiantil.repository.OrganizationRepository;
import org.agiles.bolsaestudiantil.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final StudentRepository studentRepository;
    private final OrganizationRepository organizationRepository;


    public UserEntity getUserById(Long id) {
        UserEntity user = studentRepository.findById(id).orElse(null);
        if (user == null) {
            user = organizationRepository.findById(id).orElse(null);
        }
        return user;
    }

    public UserResponseDTO userToDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(userEntity.getId());
        dto.setDescription(userEntity.getDescription());
        dto.setPhone(userEntity.getPhone());
        dto.setEmail(userEntity.getEmail());
        dto.setLocation(userEntity.getLocation());
        dto.setName(userEntity.getName());
        dto.setSurname(userEntity.getSurname());
        dto.setImageUrl(userEntity.getImageUrl());
        dto.setLinkedinUrl(userEntity.getLinkedinUrl());

        return dto;
    }
}
