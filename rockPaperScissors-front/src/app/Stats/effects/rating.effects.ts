import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, of } from 'rxjs';
import { StatsService } from '../services/stats.service';
import * as StatsActions from '../actions/stats.action';

@Injectable()
export class RatingEffects {
    constructor(private actions$: Actions, private statsService: StatsService) { }

    loadRating$ = createEffect(() =>
        this.actions$.pipe(
            ofType(StatsActions.loadRating),
            mergeMap(() =>
                this.statsService.getUsersStats().pipe(
                    map((usersStats) => StatsActions.loadRatingSuccess({ usersStats })),
                    catchError((error) => of(StatsActions.loadRatingFailure({ error })))
                )
            )
        )
    );
}
