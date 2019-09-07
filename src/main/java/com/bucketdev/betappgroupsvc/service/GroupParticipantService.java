package com.bucketdev.betappgroupsvc.service;

import com.bucketdev.betappgroupsvc.dto.GroupParticipantDTO;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
public interface GroupParticipantService {

    GroupParticipantDTO save(GroupParticipantDTO groupParticipantDTO);

    List<GroupParticipantDTO> findByTournamentId(long tournamentId);

}
