package com.example.sportradar.LiveFootBallScoreStreamer.controller;

import com.example.sportradar.LiveFootBallScoreStreamer.domain.MatchDTO;
import com.example.sportradar.LiveFootBallScoreStreamer.service.FootballScoreBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public List<MatchDTO> activeMatches()
    {
        return footballScoreBoard.activeMatches();
    }

    @PostMapping("/startGame/{homeTeam}/{awayTeam}")
    public String startGame(@PathVariable  String homeTeam ,
                            @PathVariable  String awayTeam) {
        return  footballScoreBoard.startGame(homeTeam,awayTeam) ?  "Football Match between "+homeTeam+"  "+"and"+ " "+awayTeam+" has started" :
                "Review homeTeam and AwayTeam name";
    }



    @GetMapping("/liveScore/{homeTeam}/{awayTeam}")
    public String liveScore(@PathVariable  String homeTeam ,
                            @PathVariable  String awayTeam) {
       Optional<MatchDTO> match = footballScoreBoard.liveScore(homeTeam,awayTeam);
      if(match!=null) {
          return "Live Score --- " + match.get().getHomeTeam() + " Score is" + " " + match.get().getHomeScore() +
                  " and " + match.get().getAwayTeam() + " Score is " + match.get().getAwayScore();
      }else
      {
          return "Something is Wrong with Match information";
      }
    }

    @PostMapping("/updateScore/")
    public String updateScore(@RequestBody MatchDTO match){
      return footballScoreBoard.updateScore(match) ? "Score updated" : "Something is Wrong with Match information";
    }

    @PostMapping("/finish/{homeTeam}/{awayTeam}")
    public String finishGame(@PathVariable String homeTeam,
                              @PathVariable String awayTeam)
    {
        footballScoreBoard.finishGame(homeTeam,awayTeam);
        return "Match between "+homeTeam+" and +"+awayTeam+" is finished";
    }
}
