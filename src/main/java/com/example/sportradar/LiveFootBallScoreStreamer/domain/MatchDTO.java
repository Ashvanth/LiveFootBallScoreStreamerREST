package com.example.sportradar.LiveFootBallScoreStreamer.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="MATCH")
public class MatchDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MATCH_NUMBER")
    private Long id;

    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;

    public int getTotalScore() {
        return homeScore + awayScore;
    }
}
