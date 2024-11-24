import { Component, OnInit, ViewChild } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';
import * as StatsActions from '../actions/stats.action';
import { StatsState } from '../reducers';
import { StatsDTO } from '../models/stats.dto';
import { HistoryDTO } from '../models/history.dto';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { ChartType } from 'angular-google-charts';

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.scss']
})
export class StatsComponent implements OnInit {
  stats$: Observable<StatsDTO | null>;
  history$: Observable<HistoryDTO[]>;
  stats: StatsDTO | null = null;
  history: MatTableDataSource<HistoryDTO>;
  chartType: ChartType = ChartType.PieChart
  chartMovesTitle: string = "Your used moves"
  chartSummaryTitle: string = "Summary of your plays"

  chartMovesData: any[] = [];
  chartSummaryData: any[] = []
  chartOptions = {
    titleTextStyle: {
      fontSize: 16,
      bold: true,
      italic: true,
      fontName: 'Arial',
      color: '#000',
    },
    pieHole: 0.4,
    colors: ['#ff6384', '#36a2eb', '#cc65fe'],
    legend: { position: 'bottom' },
  };

  displayedColumns: string[] = ['id', 'userMove', 'computerMove', 'result', 'playedAt'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private store: Store<{ stats: StatsState }>) {
    this.stats$ = this.store.select((state) => state.stats.stats);
    this.history$ = this.store.select((state) => state.stats.history);
    this.history = new MatTableDataSource<HistoryDTO>();
  }

  ngOnInit(): void {
    this.store.dispatch(StatsActions.loadStats());
    this.store.dispatch(StatsActions.loadHistory());

    this.stats$.pipe(take(2)).subscribe((stats) => {
      if (stats) {
        this.stats = stats;
        this.chartMovesData = [
          ['Rock', stats.rockPlayed || 0],
          ['Paper', stats.paperPlayed || 0],
          ['Scissors', stats.scissorsPlayed || 0],
        ];

        this.chartSummaryData = [
          ['Won', stats.gamesWon || 0],
          ['Lost', stats.gamesLost || 0],
          ['Tied', stats.gamesTied || 0],
        ];
      }
    });

    this.history$.pipe(take(2)).subscribe((history) => {
      this.history.data = history;
      this.history.paginator = this.paginator;
    });
  }
}
