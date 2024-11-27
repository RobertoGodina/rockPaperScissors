import { Action, createReducer, on } from '@ngrx/store';
import * as StatsActions from '../actions/stats.action';
import { StatsDTO } from '../models/stats.dto';

export interface RatingState {
    usersStats: StatsDTO[];
    loading: boolean;
    error: any;
}

export const initialStateRating: RatingState = {
    usersStats: [],
    loading: false,
    error: null,
};

export const _ratingReducer = createReducer(
    initialStateRating,
    on(StatsActions.loadRating, (state) => ({ ...state, loading: true })),
    on(StatsActions.loadRatingSuccess, (state, { usersStats }) => ({
        ...state,
        usersStats: usersStats,
        loading: false,
        error: null,
    })),
    on(StatsActions.loadRatingFailure, (state, { error }) => ({
        ...state,
        loading: false,
        error,
    }))
);

export function ratingReducer(
    state: RatingState | undefined,
    action: Action
): RatingState {
    return _ratingReducer(state, action);
}
