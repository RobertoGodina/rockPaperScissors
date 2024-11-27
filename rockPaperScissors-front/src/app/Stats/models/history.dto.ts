export class HistoryDTO {
    id: number;
    userMove: string;
    computerMove: string
    result: 'WIN' | 'LOSE' | 'TIE'
    score: number = 0;
    playedAt: Date

    constructor(
        id: number,
        userMove: string,
        computerMove: string,
        result: 'WIN' | 'LOSE' | 'TIE',
        score: number,
        playedAt: string
    ) {
        this.id = id;
        this.userMove = userMove;
        this.computerMove = computerMove;
        this.result = result;
        this.score = score;
        this.playedAt = new Date(playedAt);

    }
}
