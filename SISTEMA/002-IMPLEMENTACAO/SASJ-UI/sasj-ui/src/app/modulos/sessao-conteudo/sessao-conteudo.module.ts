import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RouterModule } from '@angular/router';

import { SegurancaModule } from './../seguranca/seguranca.module';
import { UsuarioModule } from './../usuario/usuario.module';
import { AgendamentoModule } from './../agendamento/agendamento.module';
import { RelatorioModule } from './../relatorio/relatorio.module';

import { ConteudoComponent } from './conteudo/conteudo.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    UsuarioModule,
    SegurancaModule,
    AgendamentoModule,
    RelatorioModule
  ],
  declarations: [ConteudoComponent],
  exports: [
    ConteudoComponent
  ],
  providers: []

})
export class SessaoConteudoModule { }
