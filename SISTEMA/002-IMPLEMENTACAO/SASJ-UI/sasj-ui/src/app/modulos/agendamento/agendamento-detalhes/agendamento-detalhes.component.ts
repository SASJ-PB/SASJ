import { MatSort } from '@angular/material/sort';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-agendamento-detalhes',
  templateUrl: './agendamento-detalhes.component.html',
  styleUrls: ['./agendamento-detalhes.component.css']
})
export class AgendamentoDetalhesComponent implements OnInit {

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  openDialog() {
    const dialogRef = this.dialog.open(AgendamentoDetalhesDialogComponent, {
      height: '85%'
    });
  }

}

@Component({
  selector: 'app-agendamento-detalhes-dialog',
  templateUrl: 'agendamento-detalhes-dialog.component.html',
  styleUrls: ['./agendamento-detalhes-dialog.component.css']
})
export class AgendamentoDetalhesDialogComponent implements OnInit {

  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<ParteInteressada>;

  colunasExibidas = ['nome', 'email', 'papel']; // 'acoes'

  constructor() {

    const dadosPartesInteressadas: ParteInteressada[] = [
      {nome: 'João', email: 'joao@gmail.com', papel: 'Juíz'},
      {nome: 'Maria', email: 'maria@outlook.com', papel: 'Advogada de defesa'},
      {nome: 'Rita', email: 'rita@yahoo.com', papel: 'Réu'},
      {nome: 'Marcio', email: 'marcio@gmail.com', papel: 'Advogado de acusação'},
      {nome: 'Ariel', email: 'ariel@outlook.com', papel: 'Promotora'},
    ];

    this.dataSource = new MatTableDataSource(dadosPartesInteressadas);
  }

  ngOnInit() {
    this.dataSource.sort = this.sort;
  }

}

export interface ParteInteressada {
  nome: string;
  email: string;
  papel: string;
}
