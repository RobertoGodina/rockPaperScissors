import { ActionReducerMap } from "@ngrx/store";

import * as AuthReducer from './Auth/reducers';
import { AuthEffects } from "./Auth/effects/auth.effects";

import * as UserReducer from './User/reducers';
import { UserEffects } from "./User/effects";


export interface AppState {
    auth: AuthReducer.AuthState,
    user: UserReducer.UserState
}

export const appReducers: ActionReducerMap<AppState> = {
    auth: AuthReducer.authReducer,
    user: UserReducer.userReducer
};

export const EffectsArray: any[] = [
    AuthEffects,
    UserEffects
];
