package com.bucketdev.betappgroupsvc.domain;

import com.bucketdev.betappgroupsvc.dto.GroupDTO;
import com.bucketdev.betappgroupsvc.type.PlayoffStage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author rodrigo.loyola
 */
@Entity
@Table(name = "groups")
@Data
public class Group {

    public Group(char _name) {
        name = _name;
    }

    public Group() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column
    private char name;

    @Column
    @NotNull
    private long tournamentId;

    @Column
    @NotNull
    private long tournamentUid;

    @Column
    @Enumerated(EnumType.STRING)
    private PlayoffStage playoffStage;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("points DESC, gamesWon DESC")
    private List<GroupParticipant> groupParticipants;

    public GroupDTO toDTO() {
        GroupDTO dto = new GroupDTO();

        dto.setId(id);
        dto.setName(name);
        dto.setTournamentId(tournamentId);
        dto.setPlayoffStage(playoffStage);
        dto.setGroupParticipants(groupParticipants.stream().map(GroupParticipant::toDTO).collect(Collectors.toList()));

        return dto;
    }

}


