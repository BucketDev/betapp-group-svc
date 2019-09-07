package com.bucketdev.betappgroupsvc.repository;

import com.bucketdev.betappgroupsvc.domain.GroupParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@Repository
public interface GroupParticipantRepository extends JpaRepository<GroupParticipant, Long> {

    List<GroupParticipant> findAllByTournamentId(long tournamentId);

}
