import { element } from 'protractor';
import { Router } from '@angular/router';
import { AuthService } from './../../seguranca/auth.service';
import { Audiencia, Conciliacao } from './../../core/model';
import { AgendamentoService } from './../agendamento.service';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { Component, OnInit, ViewChild, Input, AfterViewInit, Inject } from '@angular/core';
import { MatTableDataSource, MatBottomSheetRef, MatBottomSheet, MAT_BOTTOM_SHEET_DATA } from '@angular/material';
import { Observable } from 'rxjs';
import { ErrorHandlerService } from '../../core/error-handler.service';

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

  displayedColumns = ['numeroProcesso', 'agendamento', 'statusAgendamento', 'acoes']; // 'status',

  constructor(private agendamentoService: AgendamentoService,
      private authService: AuthService, private router: Router,
      private bottomSheet: MatBottomSheet) {
  }

  ngOnInit() {}

  ngAfterViewInit() {
    if (this.isAudiencia) {
      this.listarAudiencias();
    }
    else{
      this.listarConciliacoes();
    }
  }

  openBottomSheet(sessao: any): void {

    let isAudiencia = false;
    let tabelaRef: MatTableDataSource<any> = this.dataSourceConciliacoes;

    if (sessao.tipoAudiencia) {
      isAudiencia = true;
      tabelaRef = this.dataSourceAudiencias;
    }

    this.bottomSheet.open(AtualizacaoStatusAgendamentoComponent, {
      data: {
        isAudiencia: isAudiencia,
        sessao: sessao,
        tabelaRef: tabelaRef
      }
    });
  }

  listarAudiencias() {
    this.agendamentoService.listarAudiencias()
      .then(resultado => {

        // REMOVENDO SESSÕES SEM PROCESSOS
        /*const audiencias: Audiencia[] = resultado.audiencias;

        const indicesRemocao: number[] = new Array();

        for (const audiencia of audiencias){

          if (audiencia.processo.numeroProcesso === '-') {
            indicesRemocao.push(audiencias.indexOf(audiencia));
          }
        }

        for (let i = indicesRemocao.length - 1; i >= 0; i--) {
          audiencias.splice(indicesRemocao[i], 1);
        }*/

        this.dataSourceAudiencias.data = resultado.audiencias;

        this.dataSourceAudiencias.sort = this.sort;
      });
  }

  listarConciliacoes() {
    this.agendamentoService.listarConciliacoes()
      .then(resultado => {

        // REMOVENDO SESSÕES SEM PROCESSOS
        /*const conciliacoes: Conciliacao[] = resultado.conciliacoes;

        const indicesRemocao: number[] = new Array();

        for (const conciliacao of conciliacoes){

          if (conciliacao.processo.numeroProcesso === '-') {
            indicesRemocao.push(conciliacoes.indexOf(conciliacao));
          }
        }

        for (let i = indicesRemocao.length - 1; i >= 0; i--) {
          conciliacoes.splice(indicesRemocao[i], 1);
        }*/

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

@Component({
  selector: 'app-atualizacao-status-agendamento',
  template: `
    <mat-nav-list>
      <mat-list-item *ngFor="let status of opcoesStatus"
          (click)="atualizarStatus(status.nome)">
        <mat-icon mat-list-icon>{{status.icone}}</mat-icon>
        <a matLine>{{status.nome}}</a>
      </mat-list-item>
    </mat-nav-list>
  `,
  styleUrls: ['./agendamento-tabela.component.css']
})
export class AtualizacaoStatusAgendamentoComponent {

  constructor(@Inject(MAT_BOTTOM_SHEET_DATA) public data: any,
      private bottomSheetRef: MatBottomSheetRef<AtualizacaoStatusAgendamentoComponent>,
      private agendamentoService: AgendamentoService, private errorHandlerService: ErrorHandlerService) {
  }

  opcoesStatus = [
    {nome: 'ADIAR', icone: 'access_time'},
    {nome: 'CANCELAR', icone: 'clear'}
  ];

  atualizarStatus(status: string) {

    if (status === 'ADIAR') {
      status = 'ADIADO';
    }
    else {
      status = 'CANCELADO';
    }

    if (this.data.isAudiencia) {

      const audiencia: Audiencia = this.data.sessao;

      audiencia.statusAgendamento = status;
      audiencia.tipoAudiencia = this.converterTipoAudienciaLabelParaEnum(audiencia.tipoAudiencia);

      // console.log(audiencia.tipoAudiencia);

      this.agendamentoService.atualizarAudiencia(audiencia)
        .then(() => {
          this.agendamentoService.listarAudiencias()
            .then(resultado => {

              this.data.data = resultado.conciliacoes;
            }
          );
        }
      )
      .catch(erro => {
        this.errorHandlerService.handle(erro);
      });
    }
    else {
      const conciliacao: Conciliacao = this.data.sessao;

      conciliacao.statusAgendamento = status;

      this.agendamentoService.atualizarConciliacao(conciliacao)
        .then(() => {
          this.agendamentoService.listarConciliacoes()
            .then(resultado => {

              this.data.data = resultado.conciliacoes;
            }
          );
        }
      )
      .catch(erro => {
        this.errorHandlerService.handle(erro);
      });
    }

    this.bottomSheetRef.dismiss();
  }

  private converterTipoAudienciaLabelParaEnum(tipoAudiencia: any): string{
    if (tipoAudiencia === 'Ação civíl'){
      return 'ACAO_CIVIL';
    }
    else if (tipoAudiencia === 'Custódia'){
      return 'CUSTODIA';
    }
    else if (tipoAudiencia === 'Improbidade'){
      return 'IMPROBIDADE';
    }
    else if (tipoAudiencia === 'Instrução do creta'){
      return 'INSTRUCAO_CRETA';
    }
    else if (tipoAudiencia === 'Leilão'){
      return 'LEILAO';
    }
    else if (tipoAudiencia === 'Outros'){
      return 'OUTROS';
    }
    else if (tipoAudiencia === 'Penal'){
      return 'PENAL';
    }
    else if (tipoAudiencia === 'PJE'){
      return 'PJE';
    }
    else if (tipoAudiencia === 'Tebas improbidade'){
      return 'TEBAS_IMPROBIDADE';
    }
    else {
      return 'VIDEOCONFERENCIA';
    }
  }

}
