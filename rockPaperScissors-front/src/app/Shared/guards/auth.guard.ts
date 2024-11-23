import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AppState } from 'src/app/app.reducers';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  private apiToken: string = '';
  constructor(private router: Router, private store: Store<AppState>) {
    this.store.select('auth').subscribe((auth) => {
      this.apiToken = '';
      if (auth.credentials.apiToken) {
        this.apiToken = auth.credentials.apiToken;
      }
    });
  }

  canActivate():
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    if (this.apiToken) {
      return true;
    }

    this.router.navigate(['/login']);

    return false;
  }
}
