package com.bucketdev.betappgroupsvc.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Data
public class TeamDTO implements Serializable {

    private long id;
    private String uid;
    private String photoUrl;
    private String name;

}
