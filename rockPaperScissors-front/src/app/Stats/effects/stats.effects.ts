import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, of } from 'rxjs';
import * as StatsActions from '../actions/stats.action';
import { StatsService } from '../services/stats.service';

@Injectable()
export class StatsEffects {
  constructor(private actions$: Actions, private statsService: StatsService) { }

  loadStats$ = createEffect(() =>
    this.actions$.pipe(
      ofType(StatsActions.loadStats),
      mergeMap(() =>
        this.statsService.getStats().pipe(
          map((stats) => StatsActions.loadStatsSuccess({ stats })),
          catchError((error) => of(StatsActions.loadStatsFailure({ payload: error })))
        )
      )
    )
  );

  loadHistory$ = createEffect(() =>
    this.actions$.pipe(
      ofType(StatsActions.loadHistory),
      mergeMap(() =>
        this.statsService.getGameHistory().pipe(
          map((history) => StatsActions.loadHistorySuccess({ history })),
          catchError((error) => of(StatsActions.loadHistoryFailure({ payload: error })))
        )
      )
    )
  );
}
