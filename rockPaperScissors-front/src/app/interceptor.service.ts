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
import { AppState } from './app.reducers';

@Injectable({
  providedIn: 'root',
})
export class InterceptorService implements HttpInterceptor {
  access_token: string | null;

  constructor(private store: Store<AppState>) {
    this.access_token = null;
  }

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (req.url.includes("/login")) {
      return next.handle(req)
    }
    return this.store.select('auth').pipe(
      take(1),
      switchMap((auth) => {
        const apiToken = auth.credentials?.apiToken;
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
}
