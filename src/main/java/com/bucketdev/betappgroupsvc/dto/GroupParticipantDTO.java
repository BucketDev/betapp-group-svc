package com.bucketdev.betappgroupsvc.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Data
public class GroupParticipantDTO implements Serializable {

    private long id;
    private long groupId;
    private char groupName;
    private long tournamentId;
    private UserDTO user;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesTied;
    private int gamesLost;
    private int points;

}
