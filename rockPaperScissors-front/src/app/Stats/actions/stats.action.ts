import { HttpErrorResponse } from '@angular/common/http';
import { createAction, props } from '@ngrx/store';
import { StatsDTO } from '../models/stats.dto';
import { HistoryDTO } from '../models/history.dto';

export const loadStats = createAction('[Stats] Load Stats');
export const loadStatsSuccess = createAction(
    '[Stats] Load Stats Success',
    props<{ stats: StatsDTO }>()
);
export const loadStatsFailure = createAction(
    '[Stats] Load Stats Failure',
    props<{ payload: HttpErrorResponse }>()
);

export const loadHistory = createAction('[History] Load History');
export const loadHistorySuccess = createAction(
    '[History] Load History Success',
    props<{ history: HistoryDTO[] }>()
);
export const loadHistoryFailure = createAction(
    '[History] Load History Failure',
    props<{ payload: HttpErrorResponse }>()
);
