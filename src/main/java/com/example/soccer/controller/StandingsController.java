package com.example.soccer.controller;

import com.example.soccer.model.Match;
import com.example.soccer.model.Team;
import com.example.soccer.repository.MatchRepository;
import com.example.soccer.repository.TeamRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/standings")
public class StandingsController {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public StandingsController(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public List<Map<String, Object>> getStandings() {
        List<Team> teams = teamRepository.findAll();
        List<Match> matches = matchRepository.findAll();

        Map<Long, Map<String, Object>> table = new HashMap<>();

        for (Team team : teams) {
            Map<String, Object> stats = new HashMap<>();
            stats.put("team", team.getName());
            stats.put("played", 0);
            stats.put("won", 0);
            stats.put("drawn", 0);
            stats.put("lost", 0);
            stats.put("points", 0);
            stats.put("goalDifference", 0);
            table.put(team.getId(), stats);
        }

        for (Match match : matches) {
            Long homeId = match.getHomeTeam().getId();
            Long awayId = match.getAwayTeam().getId();

            int homeGoals = match.getHomeScore();
            int awayGoals = match.getAwayScore();

            updateStats(table.get(homeId), homeGoals, awayGoals);
            updateStats(table.get(awayId), awayGoals, homeGoals);
        }

        return new ArrayList<>(table.values());
    }

    private void updateStats(Map<String, Object> stats, int goalsFor, int goalsAgainst) {
        stats.put("played", (int) stats.get("played") + 1);
        stats.put("goalDifference", (int) stats.get("goalDifference") + (goalsFor - goalsAgainst));
        if (goalsFor > goalsAgainst) {
            stats.put("won", (int) stats.get("won") + 1);
            stats.put("points", (int) stats.get("points") + 3);
        } else if (goalsFor == goalsAgainst) {
            stats.put("drawn", (int) stats.get("drawn") + 1);
            stats.put("points", (int) stats.get("points") + 1);
        } else {
            stats.put("lost", (int) stats.get("lost") + 1);
        }
    }
}
