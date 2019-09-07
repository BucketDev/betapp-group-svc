package com.bucketdev.betappgroupsvc.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author rodrigo.loyola
 */
@Data
public class GroupTeamDTO implements Serializable {

    private long id;
    private long groupId;
    private long tournamentId;
    private TeamDTO team;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesTied;
    private int gamesLost;
    private int points;

}
