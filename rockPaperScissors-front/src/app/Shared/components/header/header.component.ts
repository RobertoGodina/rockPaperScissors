import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { AppState } from '../../../app.reducers';
import { logout } from '../../../Auth/actions';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit {
  showAuthSection: boolean;
  showNoAuthSection: boolean;

  constructor(
    private router: Router,
    private store: Store<AppState>
  ) {
    this.showAuthSection = false;
    this.showNoAuthSection = true;
  }

  ngOnInit(): void {

    this.store.select('auth').subscribe((authState) => {
      const apiToken = authState.credentials?.apiToken;
      if (apiToken) {
        this.showAuthSection = true;
        this.showNoAuthSection = false;
      } else {
        this.showAuthSection = false;
        this.showNoAuthSection = true;
      }
    });

  }

  play(): void {
    this.router.navigateByUrl('play');
  }

  login(): void {
    this.router.navigateByUrl('login');
  }

  register(): void {
    this.router.navigateByUrl('register');
  }

  profile(): void {
    this.router.navigateByUrl('profile');
  }

  stats(): void {
    this.router.navigateByUrl('stats');
  }

  rating(): void {
    this.router.navigateByUrl('rating');
  }

  logout(): void {
    this.store.dispatch(logout());
  }

}
