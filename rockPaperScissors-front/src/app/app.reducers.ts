import { ActionReducerMap } from "@ngrx/store";

import * as AuthReducer from './Auth/reducers';
import { AuthEffects } from "./Auth/effects/auth.effects";


export interface AppState {
    auth: AuthReducer.AuthState;
}

export const appReducers: ActionReducerMap<AppState> = {
    auth: AuthReducer.authReducer
};

export const EffectsArray: any[] = [
    AuthEffects
];
