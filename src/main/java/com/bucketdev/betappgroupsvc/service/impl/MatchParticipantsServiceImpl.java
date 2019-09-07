package com.bucketdev.betappgroupsvc.service.impl;

import com.bucketdev.betappgroupsvc.domain.Group;
import com.bucketdev.betappgroupsvc.domain.GroupParticipant;
import com.bucketdev.betappgroupsvc.domain.MatchParticipants;
import com.bucketdev.betappgroupsvc.domain.User;
import com.bucketdev.betappgroupsvc.dto.MatchParticipantsDTO;
import com.bucketdev.betappgroupsvc.exception.matchParticipants.MatchParticipantsNotFoundException;
import com.bucketdev.betappgroupsvc.repository.GroupParticipantRepository;
import com.bucketdev.betappgroupsvc.repository.GroupRepository;
import com.bucketdev.betappgroupsvc.repository.MatchParticipantsRepository;
import com.bucketdev.betappgroupsvc.service.MatchParticipantsService;
import com.bucketdev.betappgroupsvc.type.PlayoffStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class MatchParticipantsServiceImpl implements MatchParticipantsService {

    @Autowired
    private MatchParticipantsRepository repository;

    @Autowired
    private GroupParticipantRepository groupParticipantRepository;

    @Autowired
    private GroupRepository groupRepository;

    /*@Autowired
    private TournamentSettingsRepository tournamentSettingsRepository;

    @Autowired
    private TournamentService tournamentService;*/

    @Override
    public List<MatchParticipantsDTO> findAllByTournamentId(long tournamentId) {
        return repository.findAllByTournamentIdAndPlayoffStageIsNull(tournamentId).stream()
                .map(MatchParticipants::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MatchParticipantsDTO> findAllPlayoffsByTournamentId(long tournamentId) {
        return repository.findAllByTournamentIdAndPlayoffStageNotNull(tournamentId).stream()
                .map(MatchParticipants::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MatchParticipantsDTO update(MatchParticipantsDTO dto) {
        Optional<MatchParticipants> optionalMatchParticipants = repository.findById(dto.getId());
        if (!optionalMatchParticipants.isPresent())
            throw new MatchParticipantsNotFoundException("id: " + dto.getId());
        MatchParticipants matchParticipants = optionalMatchParticipants.get();

        if (dto.getScoreAway() != null && dto.getScoreHome() != null) {
            matchParticipants.setScoreAway(dto.getScoreAway());
            matchParticipants.setScoreHome(dto.getScoreHome());
            matchParticipants.setRegisteredTime(Calendar.getInstance());
            if (matchParticipants.getPlayoffStage() == null)
                calculatePoints(matchParticipants);
            else {
                //calculateNextGroup(matchParticipants);
            }
        }
        matchParticipants.setScheduledTime(dto.getScheduledTime());

        return repository.save(matchParticipants).toDTO();
    }

    private void calculatePoints(MatchParticipants matchParticipants) {
        int scoreAway = matchParticipants.getScoreAway();
        int scoreHome = matchParticipants.getScoreHome();
        GroupParticipant participantAway = matchParticipants.getGroupParticipantAway();
        GroupParticipant participantHome = matchParticipants.getGroupParticipantHome();
        participantAway.setGamesPlayed(participantAway.getGamesPlayed() + 1);
        participantHome.setGamesPlayed(participantHome.getGamesPlayed() + 1);
        if (scoreAway > scoreHome) {
            participantAway.setGamesWon(participantAway.getGamesWon() + 1);
            participantAway.setPoints(participantAway.getPoints() + 3);
            participantHome.setGamesLost(participantHome.getGamesLost() + 1);
        } else if (scoreHome > scoreAway) {
            participantHome.setGamesWon(participantHome.getGamesWon() + 1);
            participantHome.setPoints(participantHome.getPoints() + 3);
            participantAway.setGamesLost(participantAway.getGamesLost() + 1);
        } else {
            participantAway.setGamesTied(participantAway.getGamesTied() + 1);
            participantAway.setPoints(participantAway.getPoints() + 1);
            participantHome.setGamesTied(participantHome.getGamesTied() + 1);
            participantHome.setPoints(participantHome.getPoints() + 1);
        }
    }

    /*private void calculateNextGroup(MatchParticipants matchParticipants) {
        Tournament tournament = matchParticipants.getTournament();
        TournamentSettings tournamentSettings = tournamentSettingsRepository.findByTournamentUid(tournament.getUid());
        PlayoffStage playoffStage = matchParticipants.getPlayoffStage();
        Group group = matchParticipants.getGroup();
        switch (playoffStage) {
            case EIGHTH_FINALS:
                if (group.getName() == 'A' || group.getName() == 'B')
                    assignNextPlayoffGroup(matchParticipants, tournamentSettings, PlayoffStage.QUARTER_FINALS, 'A', 'A');
                if (group.getName() == 'C' || group.getName() == 'D')
                    assignNextPlayoffGroup(matchParticipants, tournamentSettings, PlayoffStage.QUARTER_FINALS, 'C', 'B');
                if (group.getName() == 'E' || group.getName() == 'F')
                    assignNextPlayoffGroup(matchParticipants, tournamentSettings, PlayoffStage.QUARTER_FINALS, 'E', 'C');
                if (group.getName() == 'G' || group.getName() == 'H')
                    assignNextPlayoffGroup(matchParticipants, tournamentSettings, PlayoffStage.QUARTER_FINALS, 'G', 'D');
                break;
            case QUARTER_FINALS:
                if (group.getName() == 'A' || group.getName() == 'B')
                    assignNextPlayoffGroup(matchParticipants, tournamentSettings, PlayoffStage.SEMIFINALS, 'A', 'A');
                if (group.getName() == 'C' || group.getName() == 'D')
                    assignNextPlayoffGroup(matchParticipants, tournamentSettings, PlayoffStage.SEMIFINALS, 'C', 'B');
                break;
            case SEMIFINALS:
                if (group.getName() == 'A' || group.getName() == 'B')
                    assignNextPlayoffGroup(matchParticipants, tournamentSettings, PlayoffStage.FINALS, 'A', 'A');
                break;
            case FINALS:
                User user = getWinnerUser(tournamentSettings, matchParticipants, playoffStage);
                if (user != null) {
                    tournament.setUserWinner(user);
                    tournament.setTournamentStage(TournamentStage.FINISHED_TOURNAMENT);
                }
                break;

        }
    }

    private void assignNextPlayoffGroup(MatchParticipants matchParticipants, TournamentSettings tournamentSettings,
                                        PlayoffStage nextPLayoffStage, char initialGroupName, char nextGroupName) {
        PlayoffStage playoffStage = matchParticipants.getPlayoffStage();
        Tournament tournament = matchParticipants.getTournament();
        Group group = matchParticipants.getGroup();

        Group nextGroup = groupRepository.findByTournamentIdAndPlayoffStageAndName(tournament.getId(), nextPLayoffStage, nextGroupName);
        //Validate if there's a winner now
        User user = getWinnerUser(tournamentSettings, matchParticipants, playoffStage);
        if (user != null) {
            if (group.getName() == initialGroupName)
                groupParticipantRepository.save(new GroupParticipant(nextGroup, user, 1));
            else
                groupParticipantRepository.save(new GroupParticipant(nextGroup, user, 0));
            Optional<Group> optionalGroup = groupRepository.findById(nextGroup.getId());
            //if all of the participants of the next group are already there, calculate match(es)
            if (optionalGroup.isPresent() && optionalGroup.get().getGroupParticipants().size() == 2)
                calculateNextMatches(tournamentSettings, optionalGroup.get(), nextPLayoffStage);
        }
    }

    private User getWinnerUser(TournamentSettings tournamentSettings, MatchParticipants matchParticipants, PlayoffStage playoffStage) {
        User user = null;
        switch (playoffStage) {
            case EIGHTH_FINALS:
                user = getUserByTotalGoals(matchParticipants, tournamentSettings.isEightFinalsRoundTrip());
                break;
            case QUARTER_FINALS:
                user = getUserByTotalGoals(matchParticipants, tournamentSettings.isQuarterFinalsRoundTrip());
                break;
            case SEMIFINALS:
                user = getUserByTotalGoals(matchParticipants, tournamentSettings.isSemiFinalsRoundTrip());
                break;
            case FINALS:
                user = getUserByTotalGoals(matchParticipants, tournamentSettings.isFinalRoundTrip());
                break;
        }
        return user;
    }*/

    private User getUserByTotalGoals(MatchParticipants matchParticipants, boolean isRoundTrip) {
        int scoreHome = 0;
        int scoreAway = 0;
        Group group = matchParticipants.getGroup();
        User user;
        if (isRoundTrip) {
            for (MatchParticipants oldMatchParticipant: repository.findByGroupId(group.getId())) {
                //If the match has not occurred yet
                if(oldMatchParticipant.getRegisteredTime() == null)
                    return null;
                if (matchParticipants.getGroupParticipantHome().equals(oldMatchParticipant.getGroupParticipantHome()))
                    scoreHome += oldMatchParticipant.getScoreHome();
                if (matchParticipants.getGroupParticipantHome().equals(oldMatchParticipant.getGroupParticipantAway()))
                    scoreHome += oldMatchParticipant.getScoreAway();
                if (matchParticipants.getGroupParticipantAway().equals(oldMatchParticipant.getGroupParticipantHome()))
                    scoreAway += oldMatchParticipant.getScoreHome();
                if (matchParticipants.getGroupParticipantAway().equals(oldMatchParticipant.getGroupParticipantAway()))
                    scoreAway += oldMatchParticipant.getScoreAway();
            }
        } else {
            scoreHome = matchParticipants.getScoreHome();
            scoreAway = matchParticipants.getScoreAway();
        }
        if (scoreHome > scoreAway)
            user = matchParticipants.getGroupParticipantHome().getUser();
        else
            user = matchParticipants.getGroupParticipantAway().getUser();
        return user;
    }

    /*private void calculateNextMatches(TournamentSettings tournamentSettings, Group group, PlayoffStage playoffStage) {
        switch (playoffStage) {
            case EIGHTH_FINALS:
                calculateMatches(group, tournamentSettings.isEightFinalsRoundTrip(), playoffStage);
                break;
            case QUARTER_FINALS:
                calculateMatches(group, tournamentSettings.isQuarterFinalsRoundTrip(), playoffStage);
                break;
            case SEMIFINALS:
                calculateMatches(group, tournamentSettings.isSemiFinalsRoundTrip(), playoffStage);
                break;
            case FINALS:
                calculateMatches(group, tournamentSettings.isFinalRoundTrip(), playoffStage);
                break;
        }
    }*/

    @Override
    public void calculateMatches(Group group, boolean roundTrip, PlayoffStage playoffStage) {
        //Tournament tournament = group.getTournament();
        long tournamentId = group.getTournamentId();
        List<GroupParticipant> groupParticipants = group.getGroupParticipants();
        int size = groupParticipants.size();
        boolean oddParticipants = groupParticipants.size() % 2 == 1;
        if (oddParticipants) {
            groupParticipants.add(new GroupParticipant());
            size++;
        }
        Stack<GroupParticipant> awayStack = new Stack<>();
        awayStack.addAll(groupParticipants.subList(0, size / 2));
        Stack<GroupParticipant> homeStack = new Stack<>();
        homeStack.addAll(groupParticipants.subList(size / 2, size));
        List<MatchParticipants> matches = new ArrayList<>();
        for (int round = 1; round < size; round++) {
            for (int i = 0; i < size / 2; i++) {
                if(awayStack.get(i).getId() > 0 && homeStack.get(i).getId() > 0) {
                    matches.add(
                        createMatch(tournamentId, group, awayStack.get(i), homeStack.get(i), round, playoffStage));
                    if (roundTrip)
                        matches.add(
                            createMatch(tournamentId, group, homeStack.get(i), awayStack.get(i),
                                oddParticipants ? round + size : round + size -1, playoffStage));
                }
            }
            if (size > 2) {
                homeStack.push(awayStack.pop());
                awayStack.add(1, homeStack.remove(0));
            }
        }
        repository.saveAll(matches);
    }

    private MatchParticipants createMatch(long tournamentId, Group group, GroupParticipant away, GroupParticipant home, int round, PlayoffStage playoffStage) {
        MatchParticipants matchParticipants = new MatchParticipants();
        matchParticipants.setTournamentId(tournamentId);
        matchParticipants.setGroup(group);
        matchParticipants.setGroupParticipantAway(away);
        matchParticipants.setGroupParticipantHome(home);
        matchParticipants.setRound(round);
        matchParticipants.setPlayoffStage(playoffStage);

        return matchParticipants;
    }

}
