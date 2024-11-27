package com.rps.rockPaperScissors.domain.stats;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Generated
public class GetAllStatsResponseVO {

    private String username;

    private int gamesWon;

    private int gamesLost;

    private int gamesTied;

    private int gamesPlayed;

    private int rockPlayed;

    private int paperPlayed;

    private int scissorsPlayed;

    private int gamePoints;
}
