import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../Models/loginRequest.dto';

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

  login(loginRequest: LoginRequest): Observable<AuthToken> {
    return this.http.post<AuthToken>(this.urlRockPaperScissorsApi + '/login', loginRequest);
  }

  logout(): Observable<void> {
    return this.http.post<void>(this.urlRockPaperScissorsApi + '/logout', null);
  }
}
