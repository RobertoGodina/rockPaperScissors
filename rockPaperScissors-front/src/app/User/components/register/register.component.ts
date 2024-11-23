import { Component } from '@angular/core';
import { UserDTO } from '../../models/user.dto';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AppState } from 'src/app/app.reducers';
import { Store } from '@ngrx/store';
import * as UserActions from '../../actions';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  registerUser: UserDTO;

  username: FormControl;
  email: FormControl;
  password: FormControl;

  registerForm: FormGroup;
  isValidForm: boolean | null;

  constructor(
    private formBuilder: FormBuilder,
    private store: Store<AppState>
  ) {
    this.registerUser = new UserDTO('', '', '', '', '');

    this.isValidForm = null;

    this.username = new FormControl(this.registerUser.username, [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(50),
    ]);

    this.email = new FormControl(this.registerUser.email, [
      Validators.required,
      Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$'),
    ]);

    this.password = new FormControl(this.registerUser.password, [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(16),
    ]);

    this.registerForm = this.formBuilder.group({
      username: this.username,
      email: this.email,
      password: this.password,
    });
  }

  ngOnInit(): void { }

  register(): void {
    this.isValidForm = false;

    if (this.registerForm.invalid) {
      return;
    }

    this.isValidForm = true;
    this.registerUser = this.registerForm.value;

    const user: UserDTO = {
      username: this.registerUser.username,
      email: this.registerUser.email,
      password: this.registerUser.password,
    };

    this.store.dispatch(UserActions.register({ user }));
  }
}
