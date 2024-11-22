import { Component, OnInit } from '@angular/core';
import {
  UntypedFormBuilder,
  UntypedFormControl,
  UntypedFormGroup,
  Validators,
} from '@angular/forms';
import { Store } from '@ngrx/store';
import { AuthState } from '../Auth/reducers/auth.reducer';
import { login } from '../Auth/actions';
import { LoginRequest } from '../Models/loginRequest.dto';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  username: UntypedFormControl;
  password: UntypedFormControl;
  loginForm: UntypedFormGroup;

  constructor(
    private formBuilder: UntypedFormBuilder,
    private store: Store<{ auth: AuthState }>,
  ) {

    this.username = new UntypedFormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(50),
    ]);

    this.password = new UntypedFormControl('', [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(16),
    ]);

    this.loginForm = this.formBuilder.group({
      username: this.username,
      password: this.password,
    });
  }

  ngOnInit(): void { }

  async login() {
    const credentials: LoginRequest = {
      username: this.username.value,
      password: this.password.value,
    };

    this.store.dispatch(login({ credentials: credentials }));
  }
}
