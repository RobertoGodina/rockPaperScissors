export class StatsDTO {
    gamesWon: number = 0;
    gamesLost: number = 0;
    gamesTied: number = 0;
    gamesPlayed: number = 0;
    rockPlayed: number = 0;
    paperPlayed: number = 0;
    scissorsPlayed: number = 0;

    constructor(
        gamesWon: number,
        gamesLost: number,
        gamesTied: number,
        gamesPlayed: number,
        rockPlayed: number,
        paperPlayed: number,
        scissorsPlayed: number,
    ) {
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.gamesTied = gamesTied;
        this.gamesPlayed = gamesPlayed;
        this.rockPlayed = rockPlayed;
        this.paperPlayed = paperPlayed;
        this.scissorsPlayed = scissorsPlayed;

    }
}
