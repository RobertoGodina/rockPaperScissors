import { createAction, props } from '@ngrx/store';
import { AuthDTO } from '../../Models/auth.dto';
import { HttpErrorResponse } from '@angular/common/http';
import { LoginRequest } from 'src/app/Models/loginRequest.dto';

export const login = createAction(
    '[Login Page] Login',
    props<{ credentials: LoginRequest }>()
);

export const loginSuccess = createAction(
    '[Login Page] Login Success',
    props<{ credentials: AuthDTO }>()
);

export const loginFailure = createAction(
    '[Login Page] Login Failure',
    props<{ payload: HttpErrorResponse }>()
);

export const logout = createAction('[Auth] Logout');
export const logoutSuccess = createAction('[Auth] Logout Success');
export const logoutFailure = createAction('[Auth] Logout Failure', (error: any) => ({ error }));
