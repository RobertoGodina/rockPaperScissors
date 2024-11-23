import { Action, createReducer, on } from '@ngrx/store';
import { AuthDTO } from '../models/auth.dto';
import { login, loginFailure, loginSuccess, logout, logoutSuccess, updateCredentials } from '../actions/auth.action';


export interface AuthState {
    credentials: AuthDTO;
    loading: boolean;
    loaded: boolean;
    error: any;
}

export const initialState: AuthState = {
    credentials: new AuthDTO('', '', '', ''),
    loading: false,
    loaded: false,
    error: null,
};

const _authReducer = createReducer(
    initialState,
    on(login, (state) => ({
        ...state,
        loading: true,
        loaded: false,
        error: null,
    })),
    on(loginSuccess, (state, action) => ({
        ...state,
        credentials: action.credentials,
        loading: false,
        loaded: true,
        error: null,
    })),
    on(loginFailure, (state, { payload }) => ({
        ...state,
        loading: false,
        loaded: false,
        error: { payload },
    })),
    on(logoutSuccess, () => initialState),
    on(updateCredentials, (state, action) => ({
        ...state,
        credentials: action.credentials,
        loading: false,
        loaded: true,
        error: null,
    })),
);

export function authReducer(
    state: AuthState | undefined,
    action: Action
): AuthState {
    return _authReducer(state, action);
}
