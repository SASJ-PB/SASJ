import { UsuarioPesquisaComponent } from './usuario-pesquisa/usuario-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './../seguranca/auth.guard';
import { UsuarioCadastroComponent } from './usuario-cadastro/usuario-cadastro.component';

const routes: Routes = [
  {
    path: 'usuarios/novo',
    component: UsuarioCadastroComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_CADASTRAR_USUARIO']}
  },
  {
    path: 'usuarios',
    component: UsuarioPesquisaComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_CADASTRAR_USUARIO']}
  },
  {
    path: 'perfil',
    component: UsuarioCadastroComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_ATUALIZAR_USUARIO']}
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class UsuarioRoutingModule { }
