import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatTabsModule } from '@angular/material/tabs';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatIconModule } from '@angular/material/icon';

import { AgendamentoPesquisaComponent } from './agendamento-pesquisa/agendamento-pesquisa.component';
import { AgendamentoTabelaComponent } from './agendamento-tabela/agendamento-tabela.component';
import { AgendamentoDetalhesComponent } from './agendamento-detalhes/agendamento-detalhes.component';
import { AgendamentoCadastroComponent } from './agendamento-cadastro/agendamento-cadastro.component';

import { AgendamentoRoutingModule } from './agendamento-routing.module';

@NgModule({
  imports: [
    CommonModule,
    AgendamentoRoutingModule,

    MatTabsModule,
    MatButtonModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatIconModule
  ],
  declarations: [
    AgendamentoPesquisaComponent,
    AgendamentoTabelaComponent,
    AgendamentoDetalhesComponent,
    AgendamentoCadastroComponent
  ]
})
export class AgendamentoModule {}
