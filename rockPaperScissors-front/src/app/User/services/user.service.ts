import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../models/register.dto';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private urlRockPaperScissorsApi: string;
  private controller: string;

  constructor(private http: HttpClient) {
    this.controller = 'auth';
    this.urlRockPaperScissorsApi = 'http://localhost:56578/api/v1/' + this.controller;
  }

  register(registerRequest: RegisterDTO): Observable<void> {
    console.log("llegoo")
    console.log(registerRequest)


    return this.http.post<void>(this.urlRockPaperScissorsApi + '/register', registerRequest);
  }
}
