package com.example.soccer.controller;

import com.example.soccer.model.Match;
import com.example.soccer.repository.MatchRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchRepository matchRepository;

    public MatchController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @GetMapping
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @GetMapping("/{id}")
    public Match getMatch(@PathVariable Long id) {
        return matchRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Match createMatch(@RequestBody Match match) {
        return matchRepository.save(match);
    }

    @PutMapping("/{id}")
    public Match updateMatch(@PathVariable Long id, @RequestBody Match updatedMatch) {
        Match match = matchRepository.findById(id).orElseThrow();
        match.setHomeTeam(updatedMatch.getHomeTeam());
        match.setAwayTeam(updatedMatch.getAwayTeam());
        match.setHomeScore(updatedMatch.getHomeScore());
        match.setAwayScore(updatedMatch.getAwayScore());
        match.setStadium(updatedMatch.getStadium());
        match.setDate(updatedMatch.getDate());
        return matchRepository.save(match);
    }

    @DeleteMapping("/{id}")
    public void deleteMatch(@PathVariable Long id) {
        matchRepository.deleteById(id);
    }
}
