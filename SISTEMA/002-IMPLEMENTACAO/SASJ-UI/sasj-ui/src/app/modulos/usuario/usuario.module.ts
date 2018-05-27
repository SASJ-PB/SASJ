import { RecuperacaoSenhaDialogComponent, RedefinicaoSenhaComponent } from './../seguranca/login/login.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SegurancaModule } from './../seguranca/seguranca.module';
import {
    UsuarioCadastroComponent,
    EmailEnviadoDialogComponent,
    DesativacaoContaDialogComponent
  } from './usuario-cadastro/usuario-cadastro.component';

import { UsuarioRoutingModule } from './usuario-routing.module';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCheckboxModule } from '@angular/material/checkbox';

import { TextMaskModule } from 'angular2-text-mask';
import { UsuarioPesquisaComponent } from './usuario-pesquisa/usuario-pesquisa.component';
import { UsuarioDetalhesComponent, UsuarioDetalhesDialogComponent } from './usuario-detalhes/usuario-detalhes.component';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,

    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatCardModule,
    MatSlideToggleModule,
    MatIconModule,
    MatDialogModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButtonToggleModule,
    MatCheckboxModule,

    TextMaskModule,

    SegurancaModule,

    UsuarioRoutingModule
  ],
  declarations: [
    UsuarioCadastroComponent,
    UsuarioPesquisaComponent,
    UsuarioDetalhesComponent,
    UsuarioDetalhesDialogComponent,
    EmailEnviadoDialogComponent,
    DesativacaoContaDialogComponent
  ],
  exports: [ UsuarioCadastroComponent ],
  entryComponents: [
    UsuarioDetalhesDialogComponent, EmailEnviadoDialogComponent, DesativacaoContaDialogComponent
  ],
})
export class UsuarioModule { }
