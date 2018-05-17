import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './../seguranca/auth.guard';
import { AgendamentoDetalhesComponent } from './agendamento-detalhes/agendamento-detalhes.component';
import { AgendamentoPesquisaComponent } from './agendamento-pesquisa/agendamento-pesquisa.component';
import { AgendamentoCadastroComponent } from './agendamento-cadastro/agendamento-cadastro.component';

const routes: Routes = [
  {
    path: 'agendamentos',
    component: AgendamentoPesquisaComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_PESQUISAR_AUDIENCIA', 'ROLE_PESQUISAR_CONCILIACAO']}
  },
  {
    path: 'agendamentos/novo',
    component: AgendamentoCadastroComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_CADASTRAR_AUDIENCIA', 'ROLE_CADASTRAR_CONCILIACAO']}
  },
  {
    path: 'agendamentos/audiencia/:codigo',
    component: AgendamentoCadastroComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_CADASTRAR_AUDIENCIA'], tipo: 'audiencia'}
  },
  {
    path: 'agendamentos/conciliacao/:codigo',
    component: AgendamentoCadastroComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_CADASTRAR_CONCILIACAO'], tipo: 'conciliacao'}
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class AgendamentoRoutingModule { }
