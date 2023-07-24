package com.example.sportradar.LiveFootBallScoreStreamer.dao;

import com.example.sportradar.LiveFootBallScoreStreamer.domain.MatchDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MatchRepositoryImpl extends JpaRepository<MatchDTO,Integer> {


}
