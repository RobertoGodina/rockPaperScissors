import { Component } from '@angular/core';
import * as GameActions from '../../actions/game.action';
import { GameState } from '../../reducers';
import { Observable, take } from 'rxjs';
import { Store } from '@ngrx/store';


@Component({
  selector: 'app-home',
  templateUrl: './play.component.html',
  styleUrl: './play.component.scss'
})
export class PlayComponent {
  choices: string[] = ['rock', 'paper', 'scissors'];
  gameState$: Observable<GameState>;
  animate: boolean = false;
  temporaryMove: string = 'rock';
  waitingForBackResponse: boolean = false
  minAnimationTime = 1500;

  constructor(private store: Store<{ game: GameState }>) {
    this.gameState$ = this.store.select('game');;
  }

  makeChoice(choice: string): void {
    this.animate = true;
    this.temporaryMove = 'rock';
    this.waitingForBackResponse = true;
    const animationStartAt = Date.now();

    this.store.dispatch(GameActions.playGame({ userMove: choice }));

    this.gameState$.pipe(take(2)).subscribe((state) => {
      if (!state.loading && this.waitingForBackResponse) {
        const elapsedTime = Date.now() - animationStartAt;
        const remainingTime = Math.max(this.minAnimationTime - elapsedTime, 0);

        setTimeout(() => {
          this.animate = false;
          this.waitingForBackResponse = false;
        }, remainingTime);
      }
    });
  }

}