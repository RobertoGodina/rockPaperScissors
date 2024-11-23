import { Component } from '@angular/core';
import { UserDTO } from '../../models/user.dto';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AppState } from 'src/app/app.reducers';
import { Store } from '@ngrx/store';
import * as UserActions from '../../actions';

@Component({
  selector: 'app-register',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {

  profileUser: UserDTO;

  username: FormControl;
  email: FormControl;
  password: FormControl;

  profileForm: FormGroup;
  isValidForm: boolean | null;

  private apiToken: string;

  constructor(
    private formBuilder: FormBuilder,
    private store: Store<AppState>
  ) {
    this.apiToken = ''
    this.profileUser = new UserDTO('', '', '', '', '');

    this.isValidForm = null;

    this.username = new FormControl(this.profileUser.username, [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(50),
    ]);

    this.email = new FormControl(this.profileUser.email, [
      Validators.required,
      Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$'),
    ]);

    this.password = new FormControl(this.profileUser.password, [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(16),
    ]);

    this.profileForm = this.formBuilder.group({
      username: this.username,
      email: this.email,
      password: this.password,
    });

    this.store.select('auth').subscribe((auth) => {
      if (auth.credentials.apiToken) {
        this.apiToken = auth.credentials.apiToken;
      }
    });

    this.store.select('user').subscribe((user) => {
      this.profileUser = user.user;

      this.username.setValue(this.profileUser.username);
      this.email.setValue(this.profileUser.email);

      this.profileForm = this.formBuilder.group({
        username: this.username,
        email: this.email,
        password: this.password,
      });
    });
  }

  ngOnInit(): void {
    if (this.apiToken) {
      this.store.dispatch(UserActions.getUserByApiToken({ apiToken: this.apiToken }));
    }
  }

  updateUser(): void {
    this.isValidForm = false;

    if (this.profileForm.invalid) {
      return;
    }

    this.isValidForm = true;
    this.profileUser = this.profileForm.value;

    const user: UserDTO = {
      username: this.profileUser.username,
      email: this.profileUser.email,
      password: this.profileUser.password,
    };

    this.store.dispatch(UserActions.updateUser({ user }));
  }
}
