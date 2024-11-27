import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { PlayComponent } from './components/play/play.component';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';


@NgModule({
    declarations: [PlayComponent],
    imports: [CommonModule, ReactiveFormsModule, MatCardModule, MatProgressSpinnerModule],
})
export class GameModule { }
