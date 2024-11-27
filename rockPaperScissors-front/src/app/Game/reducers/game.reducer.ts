import { Action, createReducer, on } from '@ngrx/store';
import * as GameActions from '../actions/game.action';


export interface GameState {
    userMove: string | null;
    computerMove: string | null;
    result: string | null;
    score: number | null;
    consecutiveWon: number | null;
    loading: boolean;
    error: any;
}

export const initialState: GameState = {
    userMove: "rock",
    computerMove: "rock",
    result: null,
    score: null,
    consecutiveWon: null,
    loading: false,
    error: null,
};

export const _gameReducer = createReducer(
    initialState,
    on(GameActions.playGame, (state, { userMove }) => ({
        ...state,
        userMove,
        computerMove: null,
        result: null,
        score: null,
        consecutiveWon: null,
        loading: true,
        error: null,
    })),
    on(GameActions.playGameSuccess, (state, { gameResponse }) => ({
        ...state,
        userMove: gameResponse.userMove,
        computerMove: gameResponse.computerMove,
        result: gameResponse.result,
        consecutiveWon: gameResponse.consecutiveWon,
        score: gameResponse.score,
        loading: false,
    })),
    on(GameActions.playGameFailure, (state, { payload }) => ({
        ...state,
        loading: false,
        error: { payload },
    })),
    on(GameActions.resetGame, () => ({
        ...initialState
    }))

);

export function gameReducer(
    state: GameState | undefined,
    action: Action
): GameState {
    return _gameReducer(state, action);
}
