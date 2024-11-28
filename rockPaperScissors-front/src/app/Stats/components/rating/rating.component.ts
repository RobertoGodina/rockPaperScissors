import { Component, OnInit, ViewChild } from '@angular/core';
import { StatsDTO } from '../../models/stats.dto';
import { Observable, take } from 'rxjs';
import { Store } from '@ngrx/store';
import { RatingState } from '../../reducers';
import * as StatsActions from '../../actions/stats.action';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrl: './rating.component.scss'
})
export class RatingComponent implements OnInit {
  rating$: Observable<StatsDTO[]>;
  rating: MatTableDataSource<StatsDTO>;

  displayedColumns: string[] = ['username', 'score'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private store: Store<{ rating: RatingState }>) {
    this.rating$ = this.store.select((state) => state.rating.usersStats);
    this.rating = new MatTableDataSource<StatsDTO>();
  }

  ngOnInit(): void {
    this.store.dispatch(StatsActions.loadRating());

    this.rating$.pipe(take(2)).subscribe((rating) => {
      const sortedRating = [...rating].sort((a, b) => b.gamePoints - a.gamePoints);

      this.rating.data = sortedRating;
      this.rating.paginator = this.paginator;
    });
  }
}
