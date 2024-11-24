import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { PlayComponent } from './components/play/play.component';
import { MatCardModule } from '@angular/material/card';

@NgModule({
    declarations: [PlayComponent],
    imports: [CommonModule, ReactiveFormsModule, MatCardModule],
})
export class GameModule { }
