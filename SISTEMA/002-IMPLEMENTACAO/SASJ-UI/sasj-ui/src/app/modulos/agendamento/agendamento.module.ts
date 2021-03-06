import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { MatTabsModule } from '@angular/material/tabs';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatSelectModule } from '@angular/material/select';
import { MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { MatListModule } from '@angular/material/list';
import { MatRadioModule } from '@angular/material/radio';
import { MatCheckboxModule } from '@angular/material/checkbox';

import { TextMaskModule } from 'angular2-text-mask';

import { AgendamentoPesquisaComponent} from './agendamento-pesquisa/agendamento-pesquisa.component';

import { AgendamentoTabelaComponent, AtualizacaoStatusAgendamentoComponent,
  AgendamentoPesquisaAvancadaComponent} from './agendamento-tabela/agendamento-tabela.component';
import { AgendamentoDetalhesComponent, AgendamentoDetalhesDialogComponent } from './agendamento-detalhes/agendamento-detalhes.component';
import { AgendamentoCadastroComponent } from './agendamento-cadastro/agendamento-cadastro.component';
import { AgendamentoRoutingModule } from './agendamento-routing.module';
import { AgendamentoRemocaoComponent, AgendamentoRemocaoDialogComponent } from './agendamento-remocao/agendamento-remocao.component';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,

    AgendamentoRoutingModule,

    TextMaskModule,

    MatTabsModule,
    MatButtonModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatIconModule,
    MatDialogModule,
    MatCardModule,
    MatDatepickerModule,
    MatSelectModule,
    MatBottomSheetModule,
    MatListModule,
    MatRadioModule,
    MatCheckboxModule
  ],
  declarations: [
    AgendamentoPesquisaComponent,
    AgendamentoTabelaComponent,
    AgendamentoDetalhesComponent,
    AgendamentoCadastroComponent,
    AgendamentoDetalhesDialogComponent,
    AgendamentoRemocaoComponent,
    AgendamentoRemocaoDialogComponent,
    AtualizacaoStatusAgendamentoComponent,
    AgendamentoPesquisaAvancadaComponent,
  ],
  providers: [
  ],
  entryComponents: [
    AgendamentoDetalhesDialogComponent,
    AgendamentoRemocaoDialogComponent,
    AtualizacaoStatusAgendamentoComponent,
    AgendamentoPesquisaAvancadaComponent
  ],
})
export class AgendamentoModule {}
