import { Component, OnInit } from '@angular/core';
import * as GameActions from '../../actions/game.action';
import { GameState } from '../../reducers';
import { Observable, take } from 'rxjs';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/app.reducers';


@Component({
  selector: 'app-home',
  templateUrl: './play.component.html',
  styleUrl: './play.component.scss'
})
export class PlayComponent implements OnInit {
  choices: string[] = ['Rock', 'Paper', 'Scissors'];
  gameState$: Observable<GameState>;
  animate: boolean = false;
  temporaryMove: string = 'rock';
  waitingForBackResponse: boolean = false
  minAnimationTime = 1500;
  showScore: boolean;


  constructor(private store: Store<AppState>) {
    this.gameState$ = this.store.select('game');;
    this.showScore = false;
  }


  ngOnInit(): void {
    this.store.select('auth').subscribe((authState) => {
      const apiToken = authState.credentials?.apiToken;
      if (apiToken) {
        this.showScore = true;
      } else {
        this.showScore = false;
      }
    });
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