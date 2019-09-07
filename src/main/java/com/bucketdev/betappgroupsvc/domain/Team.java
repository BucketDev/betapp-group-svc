package com.bucketdev.betappgroupsvc.domain;

import com.bucketdev.betappgroupsvc.dto.TeamDTO;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "teams")
@Data
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String uid;

    @Column
    private String photoUrl;

    @Column
    @NotNull
    private String name;

    public TeamDTO toDTO() {
        TeamDTO dto = new TeamDTO();

        dto.setId(id);
        dto.setUid(uid);
        dto.setPhotoUrl(photoUrl);
        dto.setName(name);

        return dto;
    }

}
