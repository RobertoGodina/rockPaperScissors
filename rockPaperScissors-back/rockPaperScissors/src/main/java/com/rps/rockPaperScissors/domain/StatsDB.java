package com.rps.rockPaperScissors.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "stats")
public class StatsDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "users_id", nullable = false)
    private UserDB user;

    private int gamesWon = 0;
    private int gamesLost = 0;
    private int gamesTied = 0;
    private int gamesPlayed = 0;
    private int rockPlayed = 0;
    private int paperPlayed = 0;
    private int scissorsPlayed = 0;
    private int gamePoints = 0;
    private int consecutiveWon = 0;
}
