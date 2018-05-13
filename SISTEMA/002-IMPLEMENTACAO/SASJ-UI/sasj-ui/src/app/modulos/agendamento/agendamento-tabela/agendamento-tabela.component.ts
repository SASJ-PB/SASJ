import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-agendamento-tabela',
  templateUrl: './agendamento-tabela.component.html',
  styleUrls: ['./agendamento-tabela.component.css']
})
export class AgendamentoTabelaComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  dataSource: MatTableDataSource<Agendamento>;

  colunasExibidas = ['processo', 'nomeParte', 'dataAudiencia', 'status', 'acoes'];

  constructor() {

    const dadosAgendamentos: Agendamento[] = [
      {processo: '1', nomeParte: 'Vinícius Otávio Gouveia Gomes', dataAudiencia: '1.0079', status: 'H'},
      {processo: '2', nomeParte: 'Helium', dataAudiencia: '0026', status: 'He'},
      {processo: '3', nomeParte: 'Lithium', dataAudiencia: '6.941', status: 'Li'},
      {processo: '4', nomeParte: 'Beryllium', dataAudiencia: '9.0122', status: 'Be'},
      {processo: '5', nomeParte: 'Boron', dataAudiencia: '10.811', status: 'B'},
      {processo: '6', nomeParte: 'Carbon', dataAudiencia: '12.0107', status: 'C'},
      {processo: '7', nomeParte: 'Nitrogen', dataAudiencia: '14.0067', status: 'N'},
      {processo: '8', nomeParte: 'Oxygen', dataAudiencia: '15.9994', status: 'O'},
      {processo: '9', nomeParte: 'Fluorine', dataAudiencia: '18.9984', status: 'F'},
      {processo: '10', nomeParte: 'Neon', dataAudiencia: '20.1797', status: 'Ne'}
    ];

    this.dataSource = new MatTableDataSource(dadosAgendamentos);
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  aplicarFiltro(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

}

export interface Agendamento {
  processo: string;
  nomeParte: string;
  dataAudiencia: string;
  status: string;
}

