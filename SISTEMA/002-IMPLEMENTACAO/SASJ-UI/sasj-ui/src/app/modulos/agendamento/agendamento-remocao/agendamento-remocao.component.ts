import { MatPaginator } from '@angular/material/paginator';
import { Conciliacao, Audiencia } from './../../core/model';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorHandlerService } from './../../core/error-handler.service';
import { AgendamentoService } from './../agendamento.service';
import { MatDialog, MAT_DIALOG_DATA, MatTableDataSource } from '@angular/material';
import { Component, OnInit, Input, Inject, TemplateRef } from '@angular/core';
import { Observable } from 'rxjs';
import { Location } from '@angular/common';

@Component({
  selector: 'app-agendamento-remocao',
  templateUrl: './agendamento-remocao.component.html',
  styleUrls: ['./agendamento-remocao.component.css']
})
export class AgendamentoRemocaoComponent implements OnInit {

  @Input() tipoSessao: string;
  @Input() idSessao: number;
  @Input() paginator: TemplateRef<MatPaginator>;

  constructor(public dialog: MatDialog) { }

  ngOnInit() {}

  openDialog() {

    //console.log(this.paginator);

    const dialogRef = this.dialog.open(AgendamentoRemocaoDialogComponent, {
      height: '45%',
      data: {
          tipoSessao: this.tipoSessao,
          idSessao: this.idSessao,
          paginatorRef: this.paginator
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

  paginatorRef: MatPaginator;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private location: Location,
      private agendamentoService: AgendamentoService, private router: Router,
      private errorHandlerService: ErrorHandlerService, private snackBar: MatSnackBar) {}

  ngOnInit() {
    console.log(this.data.paginator);
  }

  excluir() {

    this.paginatorRef = this.data.paginatorRef;

    if (this.data.tipoSessao === 'audiencia'){
      this.agendamentoService.excluirAudiencia(this.data.idSessao)
        .then(() => {
          // console.log(this.tabelaRef['dataSource']);
          // this.agendamentoService.listarAudiencias();
          //this.paginatorRef.pageIndex = 0;
          window.location.reload();

          //this.router.navigate(['/agendamentos']);

          this.snackBar.open('Audiência excluída com sucesso', '', { duration: 4500});
        })
        .catch(erro => {
          this.errorHandlerService.handle(erro);
        });
    }
    else {
      this.agendamentoService.excluirConciliacao(this.data.idSessao)
        .then(() => {
          // console.log(this.tabelaRef['dataSource']);
          window.location.reload();
          //this.router.navigate(['/agendamentos']);
          // this.agendamentoService.listarConciliacoes();
          // this.tabelaConciliacaoRef.paginator.nextPage();
          // this.tabelaConciliacaoRef.paginator.previousPage();
          this.snackBar.open('Conciliação excluída com sucesso', '', { duration: 4500});
        })
        .catch(erro => {
          this.errorHandlerService.handle(erro);
        });
    }
  }
}
