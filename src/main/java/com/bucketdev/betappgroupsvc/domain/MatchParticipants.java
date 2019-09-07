package com.bucketdev.betappgroupsvc.domain;

import com.bucketdev.betappgroupsvc.dto.MatchParticipantsDTO;
import com.bucketdev.betappgroupsvc.type.PlayoffStage;
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
@Table(name = "match_participants")
@Data
public class MatchParticipants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column
    @NotNull
    private long tournamentId;

    @ManyToOne
    @JoinColumn(name = "group_participant_away_id")
    private GroupParticipant groupParticipantAway;

    @Column
    @Min(value = 0)
    private int scoreAway;

    @ManyToOne
    @JoinColumn(name = "group_participant_home_id")
    private GroupParticipant groupParticipantHome;

    @Column
    @Min(value = 0)
    private int scoreHome;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar scheduledTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registeredTime;

    @Column
    @Enumerated(EnumType.STRING)
    private PlayoffStage playoffStage;

    @Column
    @NotNull
    private int round;

    public MatchParticipantsDTO toDTO() {
        MatchParticipantsDTO dto = new MatchParticipantsDTO();

        dto.setId(id);
        dto.setTournamentId(tournamentId);
        if (groupParticipantAway != null)
            dto.setGroupParticipantAway(groupParticipantAway.toDTO());
        dto.setScoreAway(scoreAway);
        if (groupParticipantHome != null)
            dto.setGroupParticipantHome(groupParticipantHome.toDTO());
        dto.setScoreHome(scoreHome);
        dto.setScheduledTime(scheduledTime);
        dto.setRegisteredTime(registeredTime);
        dto.setPlayoffStage(playoffStage);
        dto.setRound(round);

        return dto;
    }

}
