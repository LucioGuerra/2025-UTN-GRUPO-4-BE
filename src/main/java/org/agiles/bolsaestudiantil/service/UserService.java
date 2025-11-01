package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.internal.UserFilter;
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
import org.agiles.bolsaestudiantil.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final StudentRepository studentRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
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
        if (user == null) {
            throw new EntityNotFoundException("User not found with keycloakId: " + keycloakId);
        }

        return getUserDTOById(user.getId());
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

    public Page<UserResponseDTO> searchUsers(UserFilter filter, Pageable pageable) {
        if (filter.getSearch() == null || filter.getSearch().isBlank()) {
            return Page.empty(pageable);
        }
        
        Page<UserEntity> users = userRepository.findByNameOrSurnameContaining(filter.getSearch(), pageable);
        return users.map(this::userToDTO);
    }
}
