package com.bucketdev.betappgroupsvc.domain;

import com.bucketdev.betappgroupsvc.dto.MatchResultDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "match_results")
@Data
public class MatchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User participant;

    @Column
    @NotNull
    private long tournamentId;

    @ManyToOne
    @JoinColumn(name = "match_teams_id")
    private MatchTeams matchTeams;

    @Column
    @Min(value = 0)
    private Integer scoreAway;

    @Column
    @Min(value = 0)
    private Integer scoreHome;

    @Column
    @Min(value = 0)
    private Integer points;

    @Column
    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar creationTime;

    public MatchResultDTO toDTO() {
        MatchResultDTO dto = new MatchResultDTO();

        dto.setId(id);
        dto.setTournamentId(tournamentId);
        if (participant != null)
            dto.setParticipant(participant.toDTO());
        if (matchTeams != null)
            dto.setMatchTeamsId(matchTeams.getId());
        dto.setScoreAway(scoreAway);
        dto.setScoreHome(scoreHome);
        dto.setPoints(points);
        dto.setCreationTime(creationTime);

        return dto;
    }

}
