export class HistoryDTO {
    id: number;
    userMove: string;
    computerMove: string
    result: 'WIN' | 'LOSE' | 'TIE'
    playedAt: Date

    constructor(
        id: number,
        userMove: string,
        computerMove: string,
        result: 'WIN' | 'LOSE' | 'TIE',
        playedAt: string
    ) {
        this.id = id;
        this.userMove = userMove;
        this.computerMove = computerMove;
        this.result = result;
        this.playedAt = new Date(playedAt);

    }
}
