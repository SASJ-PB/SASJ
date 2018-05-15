import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterModule } from '@angular/router';

import { AuthService } from './../seguranca/auth.service';
import { LogoutService } from './../seguranca/logout.service';
import { UsuarioService } from './../usuario/usuario.service';
import { NaoAutorizadoComponent } from './nao-autorizado.component';
import { PaginaNaoEncontradaComponent } from './pagina-nao-encontrada.component';
import { ErrorHandlerService } from './error-handler.service';
import { NavbarModule } from './../navbar/navbar.module';

import { JwtHelper } from 'angular2-jwt';

@NgModule({
  imports: [
    CommonModule,
    NoopAnimationsModule,
    NavbarModule,
    RouterModule
  ],
  declarations: [PaginaNaoEncontradaComponent, NaoAutorizadoComponent],
  exports: [
    NavbarModule,
    MatSnackBarModule
  ],
  providers: [
    AuthService,
    LogoutService,
    JwtHelper,
    ErrorHandlerService,
    UsuarioService
  ]

})
export class CoreModule { }
