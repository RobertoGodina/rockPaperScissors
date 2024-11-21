package com.rps.rockPaperScissors.domain.achievements;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Generated
public class GetAchievementsResponseVO {

    private int gamesWon;

    private int gamesLost;

    private int gamesTied;

    private int gamesPlayed;

    private int rockPlayed;

    private int paperPlayed;

    private int scissorsPlayed;
}
