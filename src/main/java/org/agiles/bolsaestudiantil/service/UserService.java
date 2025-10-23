package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.OrganizationResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.StudentResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.UserResponseDTO;
import org.agiles.bolsaestudiantil.entity.OrganizationEntity;
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

    public UserResponseDTO getUserByKeycloakId(String keycloakId) {
        UserEntity user = studentRepository.findByKeycloakId(keycloakId).orElse(null);
        if (user == null) {
            user = organizationRepository.findByKeycloakId(keycloakId).orElse(null);
        }
        return getUserDTOById(user != null ? user.getId() : null);
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
        dto.setRole(userEntity instanceof StudentEntity ? "Student" : "Organization");

        return dto;
    }

    public UserResponseDTO getUserDTOById(Long id) {
        UserEntity user = getUserById(id);
        if (user == null) {
            return null;
        }

        if (user instanceof StudentEntity student) {
            return mapToStudentDTO(student);
        } else if (user instanceof OrganizationEntity organization) {
            return mapToOrganizationDTO(organization);
        } else {
            return mapToUserDTO(user);
        }
    }

    public StudentResponseDTO mapToStudentDTO(StudentEntity entity) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setLocation(entity.getLocation());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setImageUrl(entity.getImageUrl());
        dto.setLinkedinUrl(entity.getLinkedinUrl());
        dto.setRole("Student");

        dto.setGithubUrl(entity.getGithubUrl());
        dto.setCareer(entity.getCareer());
        dto.setCurrentYearLevel(entity.getCurrentYearLevel());
        dto.setInstitution(entity.getInstitution());
        dto.setSkills(entity.getSkills());
        dto.setIncomeDate(entity.getIncomeDate());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setCvUrl(entity.getCvUrl());
        dto.setCvFileName(entity.getCvFileName());
        dto.setCoverLetter(entity.getCoverLetter());
        return dto;
    }

    private OrganizationResponseDTO mapToOrganizationDTO(OrganizationEntity entity) {
        OrganizationResponseDTO dto = new OrganizationResponseDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setLocation(entity.getLocation());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setImageUrl(entity.getImageUrl());
        dto.setLinkedinUrl(entity.getLinkedinUrl());
        dto.setRole("Organization");

        dto.setWebSiteUrl(entity.getWebSiteUrl());
        dto.setIndustry(entity.getIndustry());
        dto.setSize(entity.getSize());
        return dto;
    }

    private UserResponseDTO mapToUserDTO(UserEntity entity) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setLocation(entity.getLocation());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setImageUrl(entity.getImageUrl());
        dto.setLinkedinUrl(entity.getLinkedinUrl());
        dto.setRole(entity instanceof StudentEntity ? "Student" : "Organization");
        return dto;
    }
}
