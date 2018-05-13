import { UsuarioCadastroComponent } from './usuario-cadastro/usuario-cadastro.component';

import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

/*
{ path: 'pessoas/novo', component: PessoaCadastroComponent, canActivate: [AuthGuard],
      data: {roles: ['ROLE_CADASTRAR_PESSOA']} }
*/

const routes: Routes = [
  { path: 'usuarios/novo', component: UsuarioCadastroComponent}
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class UsuarioRoutingModule { }
