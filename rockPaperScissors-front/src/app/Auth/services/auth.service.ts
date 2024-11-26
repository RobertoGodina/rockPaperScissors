import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginDTO } from '../models/login.dto';
import { RefreshTokenDTO } from '../models/refreshToken.dto';

interface AuthToken {
  apiToken: string;
  refreshApiToken: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private urlRockPaperScissorsApi: string;
  private controller: string;

  constructor(private http: HttpClient) {
    this.controller = 'auth';
    this.urlRockPaperScissorsApi = 'http://localhost:56578/api/v1/' + this.controller;
  }

  login(loginRequest: LoginDTO): Observable<AuthToken> {
    return this.http.post<AuthToken>(this.urlRockPaperScissorsApi + '/login', loginRequest);
  }

  logout(): Observable<void> {
    return this.http.post<void>(this.urlRockPaperScissorsApi + '/logout', null);
  }

  refreshToken(refreshApiToken: RefreshTokenDTO): Observable<AuthToken> {
    return this.http.post<AuthToken>(this.urlRockPaperScissorsApi + '/refresh', refreshApiToken);
  }
}
