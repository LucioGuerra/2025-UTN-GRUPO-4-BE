package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String keycloakId;

    @Column(length = 5000)
    private String description;

    private String phone;

    private String email;

    private String location;

    private String name;

    private String surname;

    private String imageUrl;

    private String linkedinUrl;

    public UserEntity() {
    }

}
