import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RelatorioRoutingModule } from './relatorio-routing.module';
import { RelatorioPesquisaComponent } from './relatorio-pesquisa/relatorio-pesquisa.component';
import { ChartModule } from 'primeng/chart';
import { MatButtonModule, MatInputModule, MatIconModule, MatCardModule } from '@angular/material';
import { TextMaskModule } from 'angular2-text-mask';


@NgModule({
  imports: [
    CommonModule,
    RelatorioRoutingModule,
    ReactiveFormsModule,
    FormsModule,

    TextMaskModule,

    ChartModule,

    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
  ],
  declarations: [
    RelatorioPesquisaComponent
  ]
})
export class RelatorioModule {
}
