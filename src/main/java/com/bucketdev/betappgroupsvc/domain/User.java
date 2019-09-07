package com.bucketdev.betappgroupsvc.domain;

import com.bucketdev.betappgroupsvc.dto.UserDTO;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String uid;

    @Column
    @NotNull
    private String displayName;

    @Column
    @NotNull
    private String photoUrl;

    public UserDTO toDTO() {
        UserDTO dto = new UserDTO();

        dto.setId(id);
        dto.setUid(uid);
        dto.setDisplayName(displayName);
        dto.setPhotoUrl(photoUrl);

        return dto;
    }

}
