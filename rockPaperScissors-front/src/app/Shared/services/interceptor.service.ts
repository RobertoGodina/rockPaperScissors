import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { switchMap, take } from 'rxjs/operators';
import { AppState } from '../../app.reducers';
import { AuthService } from 'src/app/Auth/services/auth.service';
import { RefreshTokenDTO } from 'src/app/Auth/models/refreshToken.dto';
import { updateCredentials } from 'src/app/Auth/actions';
import { AuthDTO } from 'src/app/Auth/models/auth.dto';

@Injectable({
  providedIn: 'root',
})
export class InterceptorService implements HttpInterceptor {

  constructor(private store: Store<AppState>, private authService: AuthService) { }

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const excludeRoutes = ["/login", "/register", "/rating"];

    if (excludeRoutes.some(route => req.url.includes(route))) {
      return next.handle(req)
    }
    return this.store.select('auth').pipe(
      take(1),
      switchMap((auth) => {
        const apiToken = auth.credentials?.apiToken;
        if (apiToken && this.isTokenExpired(apiToken)) {

          const refreshTokenRequest: RefreshTokenDTO = {
            username: auth.credentials.username,
            refreshApiToken: auth.credentials.refreshApiToken
          };

          this.authService.refreshToken(refreshTokenRequest).pipe(
            switchMap((newToken) => {
              req = req.clone({
                setHeaders: {
                  'Referrer-Policy': 'no-referrer',
                  'Content-Type': 'application/json; charset=utf-8',
                  Accept: 'application/json',
                  Authorization: `Bearer ${newToken.apiToken}`,
                },
              });

              const credentials: AuthDTO = {
                username: auth.credentials.username,
                password: auth.credentials.password,
                apiToken: newToken.apiToken,
                refreshApiToken: newToken.refreshApiToken,
              };

              console.log("Asdasdsa")

              this.store.dispatch(updateCredentials({ credentials }))

              return next.handle(req)
            })
          )
        }

        if (apiToken) {
          req = req.clone({
            setHeaders: {
              'Referrer-Policy': 'no-referrer',
              'Content-Type': 'application/json; charset=utf-8',
              Accept: 'application/json',
              Authorization: `Bearer ${apiToken}`,
            },
          });
        }

        return next.handle(req);
      })
    );
  }


  private isTokenExpired(token: string): boolean {
    const payload = this.decodeToken(token);
    if (!payload || !payload.exp) {
      return true;
    }

    const expirationDate = new Date(payload.exp * 1000);
    return expirationDate <= new Date();
  }

  private decodeToken(token: string): any {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      return JSON.parse(atob(base64));
    } catch (error) {
      console.error('Error decoding token', error);
      return null;
    }
  }
}
