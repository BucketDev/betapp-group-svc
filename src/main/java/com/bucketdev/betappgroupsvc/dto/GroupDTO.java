package com.bucketdev.betappgroupsvc.dto;

import com.bucketdev.betappgroupsvc.type.PlayoffStage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Data
public class GroupDTO implements Serializable {

    private long id;
    private char name;
    private long tournamentId;
    private PlayoffStage playoffStage;
    private List<GroupParticipantDTO> groupParticipants;

}
