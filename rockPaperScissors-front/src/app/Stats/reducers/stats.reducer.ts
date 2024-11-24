import { createReducer, on } from '@ngrx/store';
import * as StatsActions from '../actions/stats.action';
import { StatsDTO } from '../models/stats.dto';
import { HistoryDTO } from '../models/history.dto';

export interface StatsState {
    stats: StatsDTO;
    history: HistoryDTO[];
    loading: boolean;
    error: any | null;
}

export const initialState: StatsState = {
    stats: new StatsDTO(0, 0, 0, 0, 0, 0, 0),
    history: [],
    loading: false,
    error: null,
};

export const statsReducer = createReducer(
    initialState,
    on(StatsActions.loadStats, StatsActions.loadHistory, (state) => ({
        ...state,
        loading: true,
        error: null,
    })),
    on(StatsActions.loadStatsSuccess, (state, { stats }) => ({
        ...state,
        stats,
        loading: false,
    })),
    on(StatsActions.loadHistorySuccess, (state, { history }) => ({
        ...state,
        history,
        loading: false,
    })),
    on(StatsActions.loadStatsFailure, StatsActions.loadHistoryFailure, (state, { payload }) => ({
        ...state,
        loading: false,
        error: { payload },
    }))
);
