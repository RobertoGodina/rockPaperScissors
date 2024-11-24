import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PlayComponent } from './Game/components/play/play.component';
import { LoginComponent } from './Auth/components/login.component';
import { RegisterComponent } from './User/components/register/register.component';
import { ProfileComponent } from './User/components/profile/profile.component';
import { AuthGuard } from './Shared/guards/auth.guard';

const routes: Routes = [
  {
    path: '',
    component: PlayComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'play',
    component: PlayComponent,
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
