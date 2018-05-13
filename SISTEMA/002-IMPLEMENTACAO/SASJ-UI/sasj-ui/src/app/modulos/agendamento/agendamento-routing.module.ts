import { AgendamentoPesquisaComponent } from './agendamento-pesquisa/agendamento-pesquisa.component';
import { AgendamentoCadastroComponent } from './agendamento-cadastro/agendamento-cadastro.component';

import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

/*
{ path: 'pessoas/novo', component: PessoaCadastroComponent, canActivate: [AuthGuard],
      data: {roles: ['ROLE_CADASTRAR_PESSOA']} }
*/

const routes: Routes = [
  { path: 'agendamentos', component: AgendamentoPesquisaComponent},
  { path: 'agendamentos/novo', component: AgendamentoCadastroComponent}
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class AgendamentoRoutingModule { }
