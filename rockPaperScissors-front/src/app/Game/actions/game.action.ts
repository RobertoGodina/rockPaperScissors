import { createAction, props } from '@ngrx/store';
import { GameResponse } from '../service/game.service';
import { HttpErrorResponse } from '@angular/common/http';

export const playGame = createAction(
    '[Game] Play Game',
    props<{ userMove: string }>()
);

export const playGameSuccess = createAction(
    '[Game] Play Game Success',
    props<{ gameResponse: GameResponse }>()
);

export const playGameFailure = createAction(
    '[Game] Play Game Failure',
    props<{ payload: HttpErrorResponse }>()
);
