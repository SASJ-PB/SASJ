import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// import { NaoAutorizadoComponent } from './core/nao-autorizado.component';
// import { PaginaNaoEncontradaComponent } from './core/pagina-nao-encontrada.component';
// { path: 'pagina-nao-encontrada', component: PaginaNaoEncontradaComponent },
// { path: 'nao-autorizado', component: NaoAutorizadoComponent },

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full'},
];

@NgModule({

  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})

export class AppRoutingModule { }
