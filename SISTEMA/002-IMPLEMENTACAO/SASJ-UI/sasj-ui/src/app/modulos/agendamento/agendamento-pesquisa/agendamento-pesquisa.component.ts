import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from './../../seguranca/auth.service';
import { ErrorHandlerService } from './../../core/error-handler.service';
import { Processo, Audiencia, Conciliacao } from './../../core/model';
import { AgendamentoService } from './../agendamento.service';
import { Component, OnInit, Input, ViewChild, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-agendamento-pesquisa',
  templateUrl: './agendamento-pesquisa.component.html',
  styleUrls: ['./agendamento-pesquisa.component.css']
})
export class AgendamentoPesquisaComponent { /*implements OnInit {

  @ViewChild('sort') sort: MatSort;

  dataSource: MatTableDataSource<Audiencia> = new MatTableDataSource();

  displayedColumns = ['agendamento', 'acoes'];

  constructor(private agendamentoService: AgendamentoService, private router: Router,
      activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.listarAgendamentosReservados();
  }

  listarAgendamentosReservados() {
    this.agendamentoService.listarAudiencias()
      .then(resultado => {

        const agendamentosReservados: any[] = new Array();

        // REMOVENDO SESSÕES COM PROCESSOS
        const audiencias: Audiencia[] = resultado.audiencias;

        for (const audiencia of audiencias){

          if (audiencia.processo.numeroProcesso === '-') {
            agendamentosReservados.push(audiencia);
          }
        }

        /*for (let i = indicesRemocao.length - 1; i >= 0; i--) {
          audiencias.splice(indicesRemocao[i], 1);
        }

        this.dataSource.data = agendamentosReservados;

        this.dataSource.sort = this.sort;
      });
  }

  editarAgendamento(codigo: number){
    this.router.navigate(['/agendamentos/reservado', codigo]);
    // this.router.

  }*/
}
