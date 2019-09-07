package com.bucketdev.betappgroupsvc.endpoint;

import com.bucketdev.betappgroupsvc.dto.MatchParticipantsDTO;
import com.bucketdev.betappgroupsvc.service.MatchParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rodrigo.loyola
 */
@RestController
@RequestMapping("/matchParticipants")
public class MatchParticipantsController {

    @Autowired
    private MatchParticipantsService service;

    @GetMapping("tournament/{tournamentId}")
    public ResponseEntity<List<MatchParticipantsDTO>> findAllByTournamentId(@PathVariable long tournamentId) {
        return new ResponseEntity<>(service.findAllByTournamentId(tournamentId), HttpStatus.OK);
    }

    @GetMapping("tournament/{tournamentId}/playoffs")
    public ResponseEntity<List<MatchParticipantsDTO>> findAllPlayoffsByTournamentId(@PathVariable long tournamentId) {
        return new ResponseEntity<>(service.findAllPlayoffsByTournamentId(tournamentId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MatchParticipantsDTO> update(@RequestBody MatchParticipantsDTO dto) {
        return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
    }

}
