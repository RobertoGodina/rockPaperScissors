import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, finalize, map, mergeMap, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { Router } from '@angular/router';

import { login, loginFailure, loginSuccess, logout, logoutFailure, logoutSuccess } from '../actions';
import { AuthService } from '../services/auth.service';
import { ResponseError, SharedService } from 'src/app/Shared/services/shared.service';

@Injectable()
export class AuthEffects {
    private responseOK: boolean;
    private errorResponse: any;

    constructor(
        private actions$: Actions,
        private authService: AuthService,
        private router: Router,
        private sharedService: SharedService
    ) {
        this.responseOK = false;
    }

    login$ = createEffect(() =>
        this.actions$.pipe(
            ofType(login),
            mergeMap(({ credentials }) =>
                this.authService.login(credentials).pipe(
                    map((userToken) =>
                        loginSuccess({
                            credentials: {
                                ...credentials,
                                apiToken: userToken.apiToken,
                                refreshApiToken: userToken.refreshApiToken,
                            },
                        })
                    ),
                    catchError((error) =>
                        of(loginFailure({ payload: error }))
                    ),
                    finalize(async () => {
                        await this.sharedService.managementToast(
                            'loginFeedback',
                            this.responseOK,
                            this.errorResponse
                        );

                        if (this.responseOK) {
                            this.router.navigateByUrl('play');
                        }
                    })
                )
            )
        )
    );
    loginSuccess$ = createEffect(
        () =>
            this.actions$.pipe(
                ofType(loginSuccess),
                map(() => {
                    this.responseOK = true
                })
            ),
        { dispatch: false }
    );

    loginFailure$ = createEffect(
        () =>
            this.actions$.pipe(
                ofType(loginFailure),
                map((error) => {
                    this.responseOK = false;
                    this.errorResponse = {
                        statusCode: error.payload.status,
                        message: error.payload.error,
                    };;
                    this.sharedService.errorLog(error.payload.error);
                })
            ),
        { dispatch: false }
    );

    logout$ = createEffect(() =>
        this.actions$.pipe(
            ofType(logout),
            tap(() => this.authService.logout().subscribe()),
            map(() => {
                return logoutSuccess();
            }),
            catchError((error) => {
                console.error('Logout failed', error);
                return of(logoutFailure(error));
            })
        )
    );

    logoutSuccess$ = createEffect(
        () =>
            this.actions$.pipe(
                ofType(logoutSuccess),
                map(() => {
                    this.responseOK = true
                    this.router.navigateByUrl('play');
                })
            ),
        { dispatch: false }
    );

    logoutRedirect$ = createEffect(
        () =>
            this.actions$.pipe(
                ofType(loginFailure),
                map((error) => {
                    this.responseOK = false;
                    this.errorResponse = {
                        statusCode: error.payload.status,
                        message: error.payload.error,
                    };;
                    this.sharedService.errorLog(error.payload.error);
                })
            ),
        { dispatch: false }
    );
}
