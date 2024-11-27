import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StatsDTO } from '../models/stats.dto';
import { Observable } from 'rxjs';
import { HistoryDTO } from '../models/history.dto';

@Injectable({
  providedIn: 'root'
})
export class StatsService {
  private urlRockPaperScissorsApi: string;
  private statsController: string;
  private gameController: string;


  constructor(private http: HttpClient) {
    this.statsController = 'stats';
    this.gameController = 'game';
    this.urlRockPaperScissorsApi = 'http://localhost:56578/api/v1/';
  }

  getStats(): Observable<StatsDTO> {
    return this.http.get<StatsDTO>(this.urlRockPaperScissorsApi + this.statsController);
  }


  getUsersStats(): Observable<StatsDTO[]> {
    return this.http.get<StatsDTO[]>(this.urlRockPaperScissorsApi + this.statsController + '/allStats');
  }

  getGameHistory(): Observable<HistoryDTO[]> {
    return this.http.get<HistoryDTO[]>(this.urlRockPaperScissorsApi + this.gameController + '/gameHistory');
  }
}
