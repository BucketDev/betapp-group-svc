package com.bucketdev.betappgroupsvc.domain;

import com.bucketdev.betappgroupsvc.dto.GroupTeamDTO;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "group_teams")
@Data
public class GroupTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column
    @NotNull
    private long tournamentId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column
    @Min(value = 0)
    private Integer gamesPlayed;

    @Column
    @Min(value = 0)
    private Integer gamesWon;

    @Column
    @Min(value = 0)
    private Integer gamesTied;

    @Column
    @Min(value = 0)
    private Integer gamesLost;

    @Column
    @Min(value = 0)
    private Integer points;

    public GroupTeamDTO toDTO() {
        GroupTeamDTO dto = new GroupTeamDTO();

        dto.setId(id);
        dto.setTournamentId(tournamentId);
        if (group != null)
            dto.setGroupId(group.getId());
        if (team != null)
            dto.setTeam(team.toDTO());
        dto.setGamesPlayed(gamesPlayed);
        dto.setGamesWon(gamesWon);
        dto.setGamesTied(gamesTied);
        dto.setGamesLost(gamesLost);
        dto.setPoints(points);

        return dto;
    }

}
