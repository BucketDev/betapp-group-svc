package com.bucketdev.betappgroupsvc.domain;

import com.bucketdev.betappgroupsvc.dto.MatchTeamsDTO;
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
@Table(name = "match_teams")
@Data
public class MatchTeams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private long tournamentId;

    @ManyToOne
    @JoinColumn(name = "group_team_away_id")
    private GroupTeam groupTeamAway;

    @Column
    @Min(value = 0)
    private Integer scoreAway;

    @ManyToOne
    @JoinColumn(name = "group_team_home_id")
    private GroupTeam groupTeamHome;

    @Column
    @Min(value = 0)
    private Integer scoreHome;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar scheduledTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registeredTime;

    public MatchTeamsDTO toDTO() {
        MatchTeamsDTO dto = new MatchTeamsDTO();

        dto.setId(id);
        dto.setTournamentId(tournamentId);
        if(groupTeamAway != null)
            dto.setGroupTeamAway(groupTeamAway.toDTO());
        if (groupTeamHome != null)
            dto.setGroupTeamHome(groupTeamHome.toDTO());
        dto.setScoreAway(scoreAway);
        dto.setScoreHome(scoreHome);
        dto.setScheduledTime(scheduledTime);
        dto.setRegisteredTime(registeredTime);

        return dto;
    }

}
