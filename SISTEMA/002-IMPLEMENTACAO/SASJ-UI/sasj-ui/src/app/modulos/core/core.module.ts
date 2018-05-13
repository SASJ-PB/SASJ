import { ErrorHandlerService } from './error-handler.service';
import { AuthService } from './../seguranca/auth.service';

import { JwtHelper } from 'angular2-jwt';

import { NavbarModule } from './../navbar/navbar.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterModule } from '@angular/router';


@NgModule({
  imports: [
    CommonModule,
    NoopAnimationsModule,
    NavbarModule,
    RouterModule
  ],
  declarations: [],
  exports: [
    NavbarModule,
    MatSnackBarModule
  ],
  providers: [
    AuthService,
    JwtHelper,
    ErrorHandlerService
  ]

})
export class CoreModule { }
