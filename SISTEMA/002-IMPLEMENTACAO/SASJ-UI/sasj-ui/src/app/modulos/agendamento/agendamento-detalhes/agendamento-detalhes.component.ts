import { Audiencia, Conciliacao } from './../../core/model';
import { MatSort } from '@angular/material/sort';
import { Component, OnInit, ViewChild, Input, Inject } from '@angular/core';
import { MatDialog, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-agendamento-detalhes',
  templateUrl: './agendamento-detalhes.component.html',
  styleUrls: ['./agendamento-detalhes.component.css']
})
export class AgendamentoDetalhesComponent implements OnInit {

  @Input() audiencia: Audiencia;
  @Input() conciliacao: Conciliacao;
  @Input() isAudiencia: boolean;

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  openDialog() {
    const dialogRef = this.dialog.open(AgendamentoDetalhesDialogComponent, {
      height: '85%',
      data: {
        audiencia: this.audiencia,
        conciliacao: this.conciliacao,
        isAudiencia: this.isAudiencia
      }
    });
  }

}

@Component({
  selector: 'app-agendamento-detalhes-dialog',
  templateUrl: 'agendamento-detalhes-dialog.component.html',
  styleUrls: ['./agendamento-detalhes-dialog.component.css']
})
export class AgendamentoDetalhesDialogComponent implements OnInit {

  colunasExibidas = ['nome', 'email', 'papel']; // 'acoes'

  conciliacao: Conciliacao;
  audiencia: Audiencia;
  isAudiencia: boolean;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {

    this.audiencia = this.data.audiencia;
    this.conciliacao = this.data.conciliacao;
    this.isAudiencia = this.data.isAudiencia;

    console.log(this.audiencia);
    console.log(this.conciliacao);
  }

  ngOnInit() {}

}

export interface ParteInteressada {
  nome: string;
  email: string;
  papel: string;
}
