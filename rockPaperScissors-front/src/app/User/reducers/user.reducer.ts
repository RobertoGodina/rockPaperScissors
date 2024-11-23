import { Action, createReducer, on } from '@ngrx/store';
import {
    getUserByApiToken,
    getUserByApiTokenFailure,
    getUserByApiTokenSuccess,
    register,
    registerFailure,
    registerSuccess,
    updateUser,
    updateUserFailure,
    updateUserSuccess,
} from '../actions';
import { UserDTO } from '../models/user.dto';

export interface UserState {
    user: UserDTO;
    loading: boolean;
    loaded: boolean;
    error: any;
}

export const initialState: UserState = {
    user: new UserDTO('', '', '', '', ''),
    loading: false,
    loaded: false,
    error: null,
};

const _userReducer = createReducer(
    initialState,
    on(register, (state) => ({
        ...state,
        loading: true,
        loaded: false,
        error: null,
    })),
    on(registerSuccess, (state, action) => ({
        ...state,
        user: action.user,
        loading: false,
        loaded: true,
        error: null,
    })),
    on(registerFailure, (state, { payload }) => ({
        ...state,
        loading: false,
        loaded: false,
        error: { payload },
    })),
    on(updateUser, (state) => ({
        ...state,
        loading: true,
        loaded: false,
        error: null,
    })),
    on(updateUserSuccess, (state, action) => ({
        ...state,
        user: action.user,
        loading: false,
        loaded: true,
        error: null,
    })),
    on(updateUserFailure, (state, { payload }) => ({
        ...state,
        loading: false,
        loaded: false,
        error: { payload },
    })),
    on(getUserByApiToken, (state) => ({
        ...state,
        loading: true,
        loaded: false,
        error: null,
    })),
    on(getUserByApiTokenSuccess, (state, action) => ({
        ...state,
        user: action.user,
        loading: false,
        loaded: true,
        error: null,
    })),
    on(getUserByApiTokenFailure, (state, { payload }) => ({
        ...state,
        loading: false,
        loaded: false,
        error: { payload },
    }))
);

export function userReducer(
    state: UserState | undefined,
    action: Action
): UserState {
    return _userReducer(state, action);
}
