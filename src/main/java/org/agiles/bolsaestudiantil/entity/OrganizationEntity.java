package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.agiles.bolsaestudiantil.enums.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class OrganizationEntity extends  UserEntity {

    private String webSiteUrl;

    private String industry;

    private Size size;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrganizationStudentAssociationEntity> linkedStudents;

    public OrganizationEntity() {
        super();
        this.linkedStudents = new ArrayList<>();
    }
}
