import { Conciliacao, Audiencia } from './../../core/model';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorHandlerService } from './../../core/error-handler.service';
import { AgendamentoService } from './../agendamento.service';
import { MatDialog, MAT_DIALOG_DATA, MatTableDataSource } from '@angular/material';
import { Component, OnInit, Input, Inject, TemplateRef } from '@angular/core';
import { Observable } from 'rxjs';
import { Location } from '@angular/common';
import { DataSource } from '@angular/cdk/table';

@Component({
  selector: 'app-agendamento-remocao',
  template: `
  <button mat-icon-button (click)="openDialog()">
    <mat-icon>delete_forever</mat-icon>
  </button>
  `,
  styleUrls: ['./agendamento-remocao.component.css']
})
export class AgendamentoRemocaoComponent implements OnInit {

  @Input() tipoSessao: string;
  @Input() idSessao: number;
  @Input() tabelaRef: TemplateRef<MatTableDataSource<any>>;

  constructor(public dialog: MatDialog) { }

  ngOnInit() {}

  openDialog() {

    const dialogRef = this.dialog.open(AgendamentoRemocaoDialogComponent, {
      height: '45%',
      data: {
          tipoSessao: this.tipoSessao,
          idSessao: this.idSessao,
          tabelaRef: this.tabelaRef
        }
    });
  }
}

@Component({
  selector: 'app-agendamento-remocao-dialog',
  templateUrl: 'agendamento-remocao-dialog.component.html',
  styleUrls: ['./agendamento-remocao.component.css']
})
export class AgendamentoRemocaoDialogComponent implements OnInit {

  tabelaRef: MatTableDataSource<any>;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private location: Location,
      private agendamentoService: AgendamentoService, private router: Router,
      private errorHandlerService: ErrorHandlerService, private snackBar: MatSnackBar) {}

  ngOnInit() {

  }

  excluir() {

    this.tabelaRef = this.data.tabelaRef;

    if (this.data.tipoSessao === 'audiencia'){

      this.agendamentoService.excluirAudiencia(this.data.idSessao)
        .then(() => {

          // Atribuição dos dados em um array para utilizar o método 'filter'
          const dadosAudiencias: Audiencia[] = this.tabelaRef['dataSource'].data;

          // Filtragem do elemento que será removido da tabela
          const audienciaFiltrada = dadosAudiencias.filter(resultado =>
              resultado.codigo === this.data.idSessao)[0];

          // Remoção do elemento
          dadosAudiencias.splice(dadosAudiencias.indexOf(audienciaFiltrada), 1);

          // Atualização dos dados
          this.tabelaRef['dataSource'].data = dadosAudiencias;

          this.snackBar.open('Audiência excluída com sucesso', '', { duration: 4500});
        })
        .catch(erro => {
          this.errorHandlerService.handle(erro);
        });
    }
    else {

      this.agendamentoService.excluirConciliacao(this.data.idSessao)
        .then(() => {

          // Atribuição dos dados em um array para utilizar o método 'filter'
          const dadosConciliacoes: Conciliacao[] = this.tabelaRef['dataSource'].data;

          // Filtragem do elemento que será removido da tabela
          const conciliacaoFiltrada = dadosConciliacoes.filter(resultado =>
              resultado.codigo === this.data.idSessao)[0];

          // Remoção do elemento
          dadosConciliacoes.splice(dadosConciliacoes.indexOf(conciliacaoFiltrada), 1);

          // Atualização dos dados
          this.tabelaRef['dataSource'].data = dadosConciliacoes;

          this.snackBar.open('Conciliação excluída com sucesso', '', { duration: 4500});
        })
        .catch(erro => {
          this.errorHandlerService.handle(erro);
        });
    }

    this.tabelaRef['dataSource']._updateChangeSubscription();
  }
}
