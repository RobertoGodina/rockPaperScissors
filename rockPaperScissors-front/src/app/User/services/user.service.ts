import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../models/register.dto';
import { UserDTO } from '../models/user.dto';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private urlRockPaperScissorsApi: string;
  private controller: string;

  constructor(private http: HttpClient) {
    this.controller = 'user';
    this.urlRockPaperScissorsApi = 'http://localhost:56578/api/v1/' + this.controller;
  }

  register(registerRequest: RegisterDTO): Observable<void> {
    return this.http.post<void>(this.urlRockPaperScissorsApi + '/register', registerRequest);
  }

  getUser(): Observable<UserDTO> {
    return this.http.get<UserDTO>(this.urlRockPaperScissorsApi);
  }

  updateUser(user: UserDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>(this.urlRockPaperScissorsApi + "/updateUser", user);
  }
}
