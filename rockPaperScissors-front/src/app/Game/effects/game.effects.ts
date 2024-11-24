import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, of } from 'rxjs';
import * as GameActions from '../actions/game.action';
import { GameService } from '../service/game.service';

@Injectable()
export class GameEffects {
    constructor(private actions$: Actions, private gameService: GameService) { }

    playGame$ = createEffect(() =>
        this.actions$.pipe(
            ofType(GameActions.playGame),
            mergeMap(({ userMove }) =>
                this.gameService.playGame(userMove).pipe(
                    map((gameResponse) =>
                        GameActions.playGameSuccess({ gameResponse })
                    ),
                    catchError((error) =>
                        of(GameActions.playGameFailure({ payload: error }))
                    )
                )
            )
        )
    );
}
