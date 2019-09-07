package com.bucketdev.betappgroupsvc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Data
public class MatchResultDTO implements Serializable {

    private long id;
    private UserDTO participant;
    private long tournamentId;
    private long matchTeamsId;
    private int scoreAway;
    private int scoreHome;
    private int points;
    @JsonFormat(timezone = "GMT-06:00")
    private Calendar creationTime;

}
