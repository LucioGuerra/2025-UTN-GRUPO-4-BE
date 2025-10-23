package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.LanguageResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.OrganizationResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.StudentResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.UserResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.organization.OrganizationSummaryResponseDTO;
import org.agiles.bolsaestudiantil.mapper.OrganizationMapper;
import org.agiles.bolsaestudiantil.mapper.StudentMapper;
import org.agiles.bolsaestudiantil.mapper.UserMapper;
import org.agiles.bolsaestudiantil.entity.OrganizationEntity;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.entity.UserEntity;
import org.agiles.bolsaestudiantil.repository.OrganizationRepository;
import org.agiles.bolsaestudiantil.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final StudentRepository studentRepository;
    private final OrganizationRepository organizationRepository;
    private final StudentMapper studentMapper;
    private final OrganizationMapper organizationMapper;
    private final UserMapper userMapper;


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
        return userMapper.toResponseDTO(userEntity);
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
        return studentMapper.toResponseDTO(entity);
    }

    private OrganizationResponseDTO mapToOrganizationDTO(OrganizationEntity entity) {
        return organizationMapper.toResponseDTO(entity);
    }

    public OrganizationSummaryResponseDTO mapToOrganizationSummary(OrganizationEntity entity) {
        return organizationMapper.toSummaryDTO(entity);
    }

    private UserResponseDTO mapToUserDTO(UserEntity entity) {
        return userMapper.toResponseDTO(entity);
    }
}
