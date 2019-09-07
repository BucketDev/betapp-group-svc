package com.bucketdev.betappgroupsvc.service;

import com.bucketdev.betappgroupsvc.dto.GroupDTO;

import java.util.List;

public interface GroupService {

    GroupDTO save(GroupDTO dto);

    List<GroupDTO> findAllByTournamentUid(String uid);

    List<GroupDTO> findAllPlayoffsByTournamentUid(String uid);
}
