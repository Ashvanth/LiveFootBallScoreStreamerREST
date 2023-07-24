package com.example.sportradar.LiveFootBallScoreStreamer.service;

import com.example.sportradar.LiveFootBallScoreStreamer.dao.MatchRepositoryImpl;
import com.example.sportradar.LiveFootBallScoreStreamer.domain.MatchDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j

public class FootballScoreBoard {


  private static MatchRepositoryImpl MatchRepository ;


    @Autowired
    public FootballScoreBoard(MatchRepositoryImpl matchRepositoryImpl) {
        this.MatchRepository = matchRepositoryImpl;
    }

    @Transactional
    public Boolean startGame(String homeTeam, String awayTeam) {
        if(isValidTeamName(homeTeam) && isValidTeamName(awayTeam)) {
            MatchDTO matchDTO = MatchDTO.builder().
                    homeTeam(homeTeam).
                    awayTeam(awayTeam).build();
            MatchRepository.save(matchDTO);
            return true;
        } else
        {
            return false;
        }

    }
    @Transactional
    public List<MatchDTO> activeMatches()
    {
        return MatchRepository.findAll();
    }

    @Transactional
    public void finishGame(String homeTeam, String awayTeam) {
       Long matchId = findMatchId(MatchDTO.builder()
               .homeTeam(homeTeam)
               .awayTeam(awayTeam).build());
       if(matchId!= null){
        MatchRepository.deleteById(matchId.intValue());}
    }

    @Transactional
    public Optional<MatchDTO> liveScore(String homeTeam, String awayTeam){

        Long matchId = findMatchId(MatchDTO.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam).build());
       return MatchRepository.findById(matchId.intValue());
    }
    @Transactional
    public Boolean updateScore(MatchDTO matchDTO) {
        Long matchId = findMatchId(matchDTO);
        if(matchId!= null){
       Optional<MatchDTO> matchDTOFromDB = MatchRepository.findById(Integer.valueOf(matchId.intValue()));
       matchDTOFromDB.get().setHomeScore(matchDTO.getHomeScore());
        matchDTOFromDB.get().setAwayScore(matchDTO.getAwayScore());
        MatchRepository.save(matchDTOFromDB.get());
        return true;
        }else {return false;}
    }

    public Long findMatchId(MatchDTO matchDTO) {
        List<MatchDTO> matchDTOList =  MatchRepository.findAll();
        Optional<MatchDTO> matchDTOObj = matchDTOList.stream()
                .filter(matchDTO2 -> matchDTO2.getHomeTeam().equalsIgnoreCase(matchDTO.getHomeTeam()) && matchDTO2.getAwayTeam().equalsIgnoreCase(matchDTO.getAwayTeam()))
                .findFirst();
        if(matchDTOObj.isPresent()) {
            return matchDTOObj.get().getId();
        }else {return null;}
    }

    @Transactional
    public List<MatchDTO> getSummaryByTotalScore() {
        List<MatchDTO> matchDTOList =  MatchRepository.findAll();
        return matchDTOList.stream()
                .sorted(Comparator.comparingInt(MatchDTO::getTotalScore).reversed().thenComparing(matchDTOList::indexOf))
                .collect(Collectors.toList());
    }

    private boolean isValidTeamName(String teamName) {
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(teamName);
        return matcher.matches();
    }

}

