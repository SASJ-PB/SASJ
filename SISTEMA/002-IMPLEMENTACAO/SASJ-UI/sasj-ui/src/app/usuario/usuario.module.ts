import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { UsuarioCadastroComponent } from './usuario-cadastro/usuario-cadastro.component';
import { UsuariosRoutingModule } from './usuarios-routing.module';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { TextMaskModule } from 'angular2-text-mask';

@NgModule({
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    UsuariosRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MatButtonModule,
    TextMaskModule
  ],
  declarations: [ UsuarioCadastroComponent ],
  exports: [ UsuarioCadastroComponent ]
})
export class UsuarioModule { }
