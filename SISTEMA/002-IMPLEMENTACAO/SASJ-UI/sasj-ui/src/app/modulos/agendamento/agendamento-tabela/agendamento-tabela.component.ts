import { Router } from '@angular/router';
import { AuthService } from './../../seguranca/auth.service';
import { Audiencia, Conciliacao } from './../../core/model';
import { AgendamentoService } from './../agendamento.service';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-agendamento-tabela',
  templateUrl: './agendamento-tabela.component.html',
  styleUrls: ['./agendamento-tabela.component.css']
})
export class AgendamentoTabelaComponent implements OnInit {

  @Input() isAudiencia: boolean;
  @ViewChild('paginator') paginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSourceAudiencias;
  dataSourceConciliacoes;

  colunasExibidas = ['processo', 'nomeParte', 'acoes']; // 'dataAudiencia', 'status'

  constructor(private agendamentoService: AgendamentoService,
      private authService: AuthService, private router: Router) {

    // if (this.isAudiencia){
      this.listarAudiencias();
    // }
    // else{
      this.listarConciliacoes();
    // }
  }

  ngOnInit() {

  }

  aplicarFiltro(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    // this.dataSource.filter = filterValue;
  }

  listarAudiencias() {
    this.agendamentoService.listarAudiencias()
      .then(resultado => {

        // this.audiencias = resultado.audiencias;

        this.dataSourceAudiencias = new MatTableDataSource(resultado.audiencias);

        // this.dataSourceAudiencias.paginator = this.paginator;

        // this.dataSource.paginator = this.paginator;
        // this.dataSource.sort = this.sort;

        // const teste = this.audiencias.data;

      });
  }

  listarConciliacoes() {
    this.agendamentoService.listarConciliacoes()
      .then(resultado => {

        // this.audiencias = resultado.audiencias;

        this.dataSourceConciliacoes = new MatTableDataSource(resultado.conciliacoes);

        // this.dataSourceConciliacoes.paginator = this.paginator;

        // this.dataSource.paginator = this.paginator;
        // this.dataSource.sort = this.sort;

        // const teste = this.audiencias.data;
      });
  }

  editar(codigo: number){
    if (this.isAudiencia){
      this.router.navigate(['/agendamentos/audiencia', codigo]);
    }
    else{
      this.router.navigate(['/agendamentos/conciliacao', codigo]);
    }
  }

}

