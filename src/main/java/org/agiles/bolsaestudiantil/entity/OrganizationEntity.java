package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.agiles.bolsaestudiantil.enums.Size;

@Entity
@Setter
@Getter
public class OrganizationEntity extends  UserEntity {

    private String webSiteUrl;

    private String industry;

    private Size size;
}
