package com.bucketdev.betappgroupsvc.service.impl;

import com.bucketdev.betappgroupsvc.domain.Group;
import com.bucketdev.betappgroupsvc.dto.GroupDTO;
import com.bucketdev.betappgroupsvc.exception.group.GroupNotFoundException;
import com.bucketdev.betappgroupsvc.repository.GroupRepository;
import com.bucketdev.betappgroupsvc.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author rodrigo.loyola
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository repository;

    public GroupDTO save(GroupDTO dto) {
        Group group = new Group(dto.getName());
        if (dto.getId() > 0) {
            Optional<Group> groupOptional = repository.findById(dto.getId());
            if (!groupOptional.isPresent())
                throw new GroupNotFoundException("id: " + dto.getId());
            group = groupOptional.get();
        }
        return repository.save(group).toDTO();
    }

    @Override
    public List<GroupDTO> findAllByTournamentUid(String uid) {
        return repository.findAllByTournamentUid(uid).stream().map(Group::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<GroupDTO> findAllPlayoffsByTournamentUid(String uid) {
        return repository.findAllPlayoffsByTournamentUid(uid).stream().map(Group::toDTO).collect(Collectors.toList());
    }

}
