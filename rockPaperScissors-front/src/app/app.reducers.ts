import { ActionReducerMap } from "@ngrx/store";

import * as AuthReducer from './Auth/reducers';
import { AuthEffects } from "./Auth/effects/auth.effects";

import * as UserReducer from './User/reducers';
import { UserEffects } from "./User/effects";

import * as GameReducer from './Game/reducers';
import { GameEffects } from "./Game/effects/game.effects";

import * as StatsReducer from './Stats/reducers';
import { StatsEffects } from "./Stats/effects/stats.effects";
import { RatingEffects } from "./Stats/effects/rating.effects";


export interface AppState {
    auth: AuthReducer.AuthState,
    user: UserReducer.UserState,
    game: GameReducer.GameState,
    stats: StatsReducer.StatsState,
    rating: StatsReducer.RatingState
}

export const appReducers: ActionReducerMap<AppState> = {
    auth: AuthReducer.authReducer,
    user: UserReducer.userReducer,
    game: GameReducer.gameReducer,
    stats: StatsReducer.statsReducer,
    rating: StatsReducer.ratingReducer,
};

export const EffectsArray: any[] = [
    AuthEffects,
    UserEffects,
    GameEffects,
    StatsEffects,
    RatingEffects
];
