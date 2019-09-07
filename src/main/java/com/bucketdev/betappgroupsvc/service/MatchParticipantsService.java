package com.bucketdev.betappgroupsvc.service;

import com.bucketdev.betappgroupsvc.domain.Group;
import com.bucketdev.betappgroupsvc.dto.MatchParticipantsDTO;
import com.bucketdev.betappgroupsvc.type.PlayoffStage;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface MatchParticipantsService {

    List<MatchParticipantsDTO> findAllByTournamentId(long tournamentId);

    List<MatchParticipantsDTO> findAllPlayoffsByTournamentId(long tournamentId);

    MatchParticipantsDTO update(MatchParticipantsDTO dto);

    void calculateMatches(Group group, boolean roundTrip, PlayoffStage playoffStage);
}
