package com.example.sportradar.LiveFootBallScoreStreamer.controller;

import com.example.sportradar.LiveFootBallScoreStreamer.domain.Match;
import com.example.sportradar.LiveFootBallScoreStreamer.service.FootballScoreBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("data")
public class LiveStreamInputController {

    private final FootballScoreBoard footballScoreBoard;

    @Autowired
    public LiveStreamInputController(FootballScoreBoard footballScoreBoard)
    {
        this.footballScoreBoard = footballScoreBoard;
    }

    @GetMapping("/activeMatches")
    public List<Match> activeMatches()
    {
        return footballScoreBoard.activeMatches();
    }

    @PostMapping("/startGame/{homeTeam}/{awayTeam}")
    public String startGame(@PathVariable  String homeTeam ,
                            @PathVariable  String awayTeam) {
         footballScoreBoard.startGame(homeTeam,awayTeam);
         return "Football Match between "+homeTeam+"  "+"and"+ " "+awayTeam+" has started";
    }



    @GetMapping("/liveScore/{homeTeam}/{awayTeam}")
    public String liveScore(@PathVariable  String homeTeam ,
                            @PathVariable  String awayTeam) {
       Match match = footballScoreBoard.liveScore(homeTeam,awayTeam);
        return "Live Score --- "+match.getHomeTeam()+" Score is"+" "+match.getHomeScore()+
                " and "+match.getAwayTeam()+" Score is "+match.getAwayScore();
    }

    @PostMapping("/updateScore/")
    public String updateScore(@RequestBody Match match){
        footballScoreBoard.updateScore(match.getHomeTeam(),
                match.getAwayTeam(),
                match.getHomeScore(),
                match.getAwayScore());
        return "Score updated";
    }
}
