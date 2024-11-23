import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RegisterComponent } from './components/register/register.component';


@NgModule({
    declarations: [RegisterComponent],
    imports: [CommonModule, ReactiveFormsModule],
})
export class UserModule { }