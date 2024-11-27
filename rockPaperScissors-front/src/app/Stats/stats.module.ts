import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { StatsComponent } from './components/userStats/stats.component';
import { MatChipsModule } from '@angular/material/chips';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { GoogleChartsModule } from 'angular-google-charts';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { RatingComponent } from './components/rating/rating.component';

@NgModule({
    declarations: [StatsComponent, RatingComponent],
    imports: [
        CommonModule,
        MatCardModule,
        MatChipsModule,
        MatTableModule,
        MatPaginatorModule,
        GoogleChartsModule,
        MatFormFieldModule,
        MatInputModule],
})
export class StatsModule { }
