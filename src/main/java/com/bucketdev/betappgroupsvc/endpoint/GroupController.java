package com.bucketdev.betappgroupsvc.endpoint;

import com.bucketdev.betappgroupsvc.dto.GroupDTO;
import com.bucketdev.betappgroupsvc.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService service;

    @GetMapping("/tournament/{uid}")
    public ResponseEntity<List<GroupDTO>> findByTournamentUid(@PathVariable String uid) {
        return new ResponseEntity<>(service.findAllByTournamentUid(uid), HttpStatus.OK);
    }

    @GetMapping("/tournament/{uid}/playoffs")
    public ResponseEntity<List<GroupDTO>> findAllPlayoffsByTournamentUid(@PathVariable String uid) {
        return new ResponseEntity<>(service.findAllPlayoffsByTournamentUid(uid), HttpStatus.OK);
    }

}
