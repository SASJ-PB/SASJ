import { Router } from '@angular/router';
import { AuthService } from './../../seguranca/auth.service';
import { Audiencia, Conciliacao } from './../../core/model';
import { AgendamentoService } from './../agendamento.service';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { Component, OnInit, ViewChild, Input, AfterViewInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-agendamento-tabela',
  templateUrl: './agendamento-tabela.component.html',
  styleUrls: ['./agendamento-tabela.component.css']
})
export class AgendamentoTabelaComponent implements OnInit, AfterViewInit {

  @Input() isAudiencia: boolean;
  @ViewChild('sort') sort: MatSort;

  dataSourceAudiencias: MatTableDataSource<Audiencia> = new MatTableDataSource();
  dataSourceConciliacoes: MatTableDataSource<Conciliacao> = new MatTableDataSource();

  // displayedColumns = ['agendamento', 'acoes']; // 'status',
  displayedColumns = ['tipoAudiencia', 'quantidadeOitivas', 'acoes'];
  displayedColumnsConciliacoes = ['nomeConciliador', 'duracaoEstimada', 'acoes'];

  constructor(private agendamentoService: AgendamentoService,
      private authService: AuthService, private router: Router) {
  }

  ngOnInit() {}

  ngAfterViewInit() {
    this.listarAudiencias();
    this.listarConciliacoes();
  }

  listarAudiencias() {
    this.agendamentoService.listarAudiencias()
      .then(resultado => {

        this.dataSourceAudiencias.data = resultado.audiencias;

        this.dataSourceAudiencias.sort = this.sort;
      });
  }

  listarConciliacoes() {
    this.agendamentoService.listarConciliacoes()
      .then(resultado => {

        this.dataSourceConciliacoes.data = resultado.conciliacoes;

        this.dataSourceConciliacoes.sort = this.sort;
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
