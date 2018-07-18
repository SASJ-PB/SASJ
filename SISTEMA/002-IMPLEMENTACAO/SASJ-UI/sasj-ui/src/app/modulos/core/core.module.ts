import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterModule } from '@angular/router';

import { AuthService } from './../seguranca/auth.service';
import { LogoutService } from './../seguranca/logout.service';
import { UsuarioService } from './../usuario/usuario.service';
import { AgendamentoService } from './../agendamento/agendamento.service';
import { StorageDataService } from './storage-data.service';
import { RelatorioService } from './../relatorio/relatorio.service';

import { NaoAutorizadoComponent } from './nao-autorizado.component';
import { PaginaNaoEncontradaComponent } from './pagina-nao-encontrada.component';
import { ErrorHandlerService } from './error-handler.service';
import { NavbarModule } from './../navbar/navbar.module';

import { JwtHelper } from 'angular2-jwt';

import { LoadingComponent } from './loading.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  imports: [
    CommonModule,
    NoopAnimationsModule,
    NavbarModule,
    RouterModule,
    MatProgressSpinnerModule,
    MatDialogModule
  ],
  declarations: [PaginaNaoEncontradaComponent, NaoAutorizadoComponent, LoadingComponent],
  exports: [
    NavbarModule,
    MatSnackBarModule,
    MatDialogModule
  ],
  providers: [
    AuthService,
    LogoutService,
    JwtHelper,
    ErrorHandlerService,
    UsuarioService,
    AgendamentoService,
    StorageDataService,
    RelatorioService
  ],
  entryComponents: [
    LoadingComponent
  ],
})
export class CoreModule { }
