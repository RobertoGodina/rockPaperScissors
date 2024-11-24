import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { catchError, exhaustMap, finalize, map, take, tap } from 'rxjs/operators';
import * as UserActions from '../actions';
import { UserService } from '../services/user.service';
import { SharedService } from 'src/app/Shared/services/shared.service';
import { updateCredentials } from 'src/app/Auth/actions';
import { AuthDTO } from 'src/app/Auth/models/auth.dto';
import { Store } from '@ngrx/store';

@Injectable()
export class UserEffects {
  private responseOK: boolean;
  private errorResponse: any;

  constructor(
    private actions$: Actions,
    private userService: UserService,
    private router: Router,
    private sharedService: SharedService,
    private store: Store
  ) {
    this.responseOK = false;
  }

  register$ = createEffect(() =>
    this.actions$.pipe(
      ofType(UserActions.register),
      exhaustMap(({ user }) =>
        this.userService.register(user).pipe(
          map((User) => {
            return UserActions.registerSuccess({ user: user });
          }),
          catchError((error) => {
            return of(UserActions.registerFailure({ payload: error }));
          }),
          finalize(async () => {
            await this.sharedService.managementToast(
              'registerFeedback',
              this.responseOK,
              this.errorResponse
            );

            if (this.responseOK) {
              this.router.navigateByUrl('login');
            }
          })
        )
      )
    )
  );

  registerSuccess$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(UserActions.registerSuccess),
        map(() => {
          this.responseOK = true;
        })
      ),
    { dispatch: false }
  );

  registerFailure$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(UserActions.registerFailure),
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

  getUserByApiToken$ = createEffect(() =>
    this.actions$.pipe(
      ofType(UserActions.getUserByApiToken),
      exhaustMap(({ apiToken }) =>
        this.userService.getUser().pipe(
          map((User) => {
            return UserActions.getUserByApiTokenSuccess({ user: User });
          }),
          catchError((error) => {
            return of(UserActions.getUserByApiTokenFailure({ payload: error }));
          }),
        )
      )
    )
  );

  getUserByIdFailure$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(UserActions.getUserByApiTokenFailure),
        map((error) => {
          this.responseOK = false;
          this.errorResponse = {
            statusCode: error.payload.status,
            message: error.payload.error,
          };
          this.sharedService.errorLog(error.payload.error);
        })
      ),
    { dispatch: false }
  );

  updateUser$ = createEffect(() =>
    this.actions$.pipe(
      ofType(UserActions.updateUser),
      exhaustMap(({ user }) =>
        this.userService.updateUser(user).pipe(
          map((User) => {

            return UserActions.updateUserSuccess({ user: User });
          }),
          catchError((error) => {
            return of(UserActions.updateUserFailure({ payload: error }));
          }),
          finalize(() => {
            if (this.responseOK) {
              this.router.navigateByUrl('play');
            }
          })
        )
      )
    )
  );

  updateUserSuccess$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(UserActions.updateUserSuccess),
        tap(({ user }) => {
          this.responseOK = true;
          const credentials: AuthDTO = {
            username: user.username,
            password: user.password,
            apiToken: user.apiToken!,
            refreshApiToken: user.refreshApiToken!,
          };
          this.store.dispatch(updateCredentials({ credentials }));
        })
      ),
    { dispatch: false }
  );

  updateUserFailure$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(UserActions.updateUserFailure),
        map((error) => {
          this.responseOK = false;
          this.errorResponse = {
            statusCode: error.payload.status,
            message: error.payload.error,
          };
          this.sharedService.errorLog(error.payload.error);
        })
      ),
    { dispatch: false }
  );

}
