import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface GameResponse {
  userMove: string;
  computerMove: string;
  result: string;
}

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private urlRockPaperScissorsApi: string;
  private controller: string;

  constructor(private http: HttpClient) {
    this.controller = 'game';
    this.urlRockPaperScissorsApi = 'http://localhost:56578/api/v1/' + this.controller;
  }

  playGame(userMove: string): Observable<GameResponse> {
    const params = new HttpParams().set('userMove', userMove.toLocaleUpperCase());
    return this.http.get<GameResponse>(this.urlRockPaperScissorsApi + '/play', { params });
  }
}

