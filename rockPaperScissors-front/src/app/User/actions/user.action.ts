import { HttpErrorResponse } from '@angular/common/http';
import { createAction, props } from '@ngrx/store';
import { UserDTO } from '../models/user.dto';

export const register = createAction(
    '[Register Page] Register new user',
    props<{ user: UserDTO }>()
);
export const registerSuccess = createAction(
    '[Register Page] Register new user Success',
    props<{ user: UserDTO }>()
);

export const registerFailure = createAction(
    '[Register Page] Register new user Failure',
    props<{ payload: HttpErrorResponse }>()
);

export const updateUser = createAction(
    '[Profile Page] Update User',
    props<{ apiToken: string; user: UserDTO }>()
);
export const updateUserSuccess = createAction(
    '[Profile Page] Update User Success',
    props<{ apiToken: string; user: UserDTO }>()
);

export const updateUserFailure = createAction(
    '[Profile Page] Update User Failure',
    props<{ payload: HttpErrorResponse }>()
);

export const getUserByApiToken = createAction(
    '[Profile Page] Get user by ApiToken',
    props<{ apiToken: string }>()
);
export const getUserByApiTokenSuccess = createAction(
    '[Profile Page] Get user by ApiToken Success',
    props<{ apiToken: string; user: UserDTO }>()
);

export const getUserByApiTokenFailure = createAction(
    '[Profile Page] Get user byApiToken Failure',
    props<{ payload: HttpErrorResponse }>()
);
