package com.example.sportradar.LiveFootBallScoreStreamer;

import com.example.sportradar.LiveFootBallScoreStreamer.dao.MatchRepositoryImpl;
import com.example.sportradar.LiveFootBallScoreStreamer.domain.MatchDTO;
import com.example.sportradar.LiveFootBallScoreStreamer.service.FootballScoreBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class LiveFootBallScoreStreamerApplicationTests {

	@Test
	void contextLoads() {
	}

	private FootballScoreBoard scoreBoard;

	private MatchDTO matchDTO;

	@Autowired
	private MatchRepositoryImpl matchRepository;

	@BeforeEach
	public void setUp() {
		scoreBoard = new FootballScoreBoard(matchRepository);
	}

	@Test
	@Order(1)
	public void testStartGame() throws SQLException {
		scoreBoard.startGame("France", "Argentina");
		scoreBoard.startGame("Japan", "China");
		List<MatchDTO> summary = scoreBoard.getSummaryByTotalScore();
		assertEquals("Argentina", summary.get(0).getAwayTeam());
		assertEquals("France", summary.get(0).getHomeTeam());
		assertEquals(0,summary.get(0).getHomeScore());
		assertEquals(0,summary.get(0).getAwayScore());
	}

	@Test
	@Order(2)
	public void testUpdateGame() {
		matchDTO = matchDTO.builder()
				.homeTeam("France")
				.homeScore(3)
				.awayTeam("Argentina")
				.awayScore(6).build();
		scoreBoard.updateScore(matchDTO);
		List<MatchDTO> summary = scoreBoard.getSummaryByTotalScore();
		assertEquals("Argentina", summary.get(0).getAwayTeam());
		assertEquals("France", summary.get(0).getHomeTeam());
		assertEquals(3,summary.get(0).getHomeScore());
		assertEquals(6,summary.get(0).getAwayScore());
	}

	@Test
	@Order(3)
	public void testFinishGame() {
		scoreBoard.finishGame("Japan","China");
		List<MatchDTO> summary = scoreBoard.getSummaryByTotalScore();
		assertNotEquals("China", summary.get(0).getAwayTeam());
		assertNotEquals("Japan", summary.get(0).getHomeTeam());
		assertEquals("France",summary.get(0).getHomeTeam());
		assertEquals("Argentina",summary.get(0).getAwayTeam());
	}
}
