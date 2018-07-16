import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './../seguranca/auth.guard';
import { RelatorioPesquisaComponent } from './relatorio-pesquisa/relatorio-pesquisa.component';

const routes: Routes = [
  {
    path: 'relatorios',
    component: RelatorioPesquisaComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_PESQUISAR_AUDIENCIA', 'ROLE_PESQUISAR_CONCILIACAO']}
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class RelatorioRoutingModule { }
