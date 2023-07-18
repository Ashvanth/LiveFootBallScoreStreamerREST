package com.example.sportradar.LiveFootBallScoreStreamer;

import com.example.sportradar.LiveFootBallScoreStreamer.domain.Match;
import com.example.sportradar.LiveFootBallScoreStreamer.service.FootballScoreBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class LiveFootBallScoreStreamerApplicationTests {

	@Test
	void contextLoads() {
	}

	private FootballScoreBoard scoreBoard;

	@BeforeEach
	public void setUp() {
		scoreBoard = new FootballScoreBoard();
	}

	@Test
	public void testStartGame() {
		scoreBoard.startGame("Germany", "Canada");
		List<Match> summary = scoreBoard.getSummaryByTotalScore();
		assertEquals(1, summary.size());
		assertEquals("Canada", summary.get(0).getAwayTeam());
		assertEquals("Germany", summary.get(0).getHomeTeam());
		assertEquals(0,summary.get(0).getHomeScore());
		assertEquals(0,summary.get(0).getAwayScore());
	}

	@Test
	public void testUpdateGame() {
		scoreBoard.startGame("Germany", "Canada");
		scoreBoard.updateScore("Germany", "Canada",5,10);

		List<Match> summary = scoreBoard.getSummaryByTotalScore();
		assertEquals("Canada", summary.get(0).getAwayTeam());
		assertEquals("Germany", summary.get(0).getHomeTeam());
		assertEquals(5,summary.get(0).getHomeScore());
		assertEquals(10,summary.get(0).getAwayScore());
	}

	@Test
	public void testFinishGame() {
		scoreBoard.startGame("Germany", "Canada");
		scoreBoard.startGame("Spain", "Brazil");
		scoreBoard.startGame("Italy", "France");
		scoreBoard.updateScore("Germany", "Canada",5,10);
		scoreBoard.finishGame("Germany","Canada");

		List<Match> summary = scoreBoard.getSummaryByTotalScore();
		assertNotEquals("Canada", summary.get(0).getAwayTeam());
		assertNotEquals("Germany", summary.get(0).getHomeTeam());
		assertEquals("Spain",summary.get(0).getHomeTeam());
		assertEquals("Brazil",summary.get(0).getAwayTeam());
	}

	@Test
	public void testSummaryOfGame() {
		scoreBoard.startGame("Germany", "Canada");
		scoreBoard.updateScore("Germany", "Canada",5,10);

		List<Match> summary = scoreBoard.getSummaryByTotalScore();
		assertEquals("Canada", summary.get(0).getAwayTeam());
		assertEquals("Germany", summary.get(0).getHomeTeam());
		assertEquals(5,summary.get(0).getHomeScore());
		assertEquals(10,summary.get(0).getAwayScore());
	}
}
