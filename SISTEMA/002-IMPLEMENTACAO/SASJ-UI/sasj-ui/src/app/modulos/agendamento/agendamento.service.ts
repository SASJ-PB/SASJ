import { environment } from './../../../environments/environment';
import { AuthHttp } from 'angular2-jwt';
import { Audiencia, Conciliacao, Processo, AudienciaFilter, ConciliacaoFilter } from './../core/model';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Headers, URLSearchParams } from '@angular/http';
import * as moment from 'moment';

@Injectable()
export class AgendamentoService {

  audienciasUrl: string;
  conciliacoesUrl: string;
  processosUrl: string;

  constructor(private http: AuthHttp) {
    this.audienciasUrl = `${environment.apiUrl}/audiencias`;
    this.conciliacoesUrl = `${environment.apiUrl}/conciliacoes`;
    this.processosUrl = `${environment.apiUrl}/processos`;
  }

  cadastrarAudiencia(audiencia: Audiencia): Promise<Audiencia>{
    return this.http.post(this.audienciasUrl, JSON.stringify(audiencia))
      .toPromise()
      .then(response => response.json());
  }

  cadastrarConciliacao(conciliacao: Conciliacao): Promise<Conciliacao>{
    return this.http.post(this.conciliacoesUrl, JSON.stringify(conciliacao))
      .toPromise()
      .then(response => response.json());
  }

  listarAudiencias(): Promise<any> {
    return this.http.get(this.audienciasUrl)
      .toPromise()
      .then(response => {

        const resultado = {
          audiencias: response.json()
        };

        return resultado;
      });
  }

  listarConciliacoes(): Promise<any> {
    return this.http.get(this.conciliacoesUrl)
      .toPromise()
      .then(response => {

        const resultado = {
          conciliacoes: response.json()
        };

        return resultado;
      });
  }

  excluirAudiencia(codigo: number): Promise<void> {
    return this.http.delete(`${this.audienciasUrl}/${codigo}`)
      .toPromise()
      .then(() => null);
  }

  excluirConciliacao(codigo: number): Promise<void> {
    return this.http.delete(`${this.conciliacoesUrl}/${codigo}`)
      .toPromise()
      .then(() => null);
  }

  buscarAudienciaPorCodigo(codigo: number): Promise<Audiencia> {
    return this.http.get(`${this.audienciasUrl}/${codigo}`)
      .toPromise()
      .then(response => {

        const audiencia = response.json() as Audiencia;

        return audiencia;
      });
  }

  buscarConciliacaoPorCodigo(codigo: number): Promise<Conciliacao> {
    return this.http.get(`${this.conciliacoesUrl}/${codigo}`)
      .toPromise()
      .then(response => {

        const conciliacao = response.json() as Conciliacao;

        return conciliacao;
      });
  }

  atualizarAudiencia(audiencia: Audiencia): Promise<Audiencia> {
    // console.log(audiencia);
    return this.http.put(`${this.audienciasUrl}/${audiencia.codigo}`,
        JSON.stringify(audiencia))
      .toPromise()
      .then(response => {
        const audienciaAlterada = response.json() as Audiencia;
        return audienciaAlterada;
      });
  }

  atualizarConciliacao(conciliacao: Conciliacao): Promise<Conciliacao> {
    return this.http.put(`${this.conciliacoesUrl}/${conciliacao.codigo}`,
        JSON.stringify(conciliacao))
      .toPromise()
      .then(response => {
        const conciliacaoAlterada = response.json() as Conciliacao;
        return conciliacaoAlterada;
      });
  }

  atualizarProcesso(processo: Processo): Promise<Processo> {
    return this.http.put(`${this.processosUrl}/${processo.codigo}`,
        JSON.stringify(processo))
      .toPromise()
      .then(response => {
        const processoAlterado = response.json() as Processo;
        return processoAlterado;
      });
  }

  filtrarAudiencia(audienciafilter: AudienciaFilter): Promise<any> {

    const params = new URLSearchParams();

    if (audienciafilter.dataAgendamentoDe) {
      params.set('dataAgendamentoDe', audienciafilter.dataAgendamentoDe);
    }

    if (audienciafilter.dataAgendamentoAte) {
      params.set('dataAgendamentoAte', audienciafilter.dataAgendamentoAte);
    }

    if (audienciafilter.statusAgendamento) {
      params.set('statusAgendamento', audienciafilter.statusAgendamento);
    }

    if (audienciafilter.tipoAudiencia) {
      params.set('tipoAudiencia', audienciafilter.tipoAudiencia);
    }

    if (audienciafilter.quantidadeOitivasDe) {
      params.set('quantidadeOitivasDe', audienciafilter.quantidadeOitivasDe);
    }

    if (audienciafilter.quantidadeOitivasAte) {
      params.set('quantidadeOitivasAte', audienciafilter.quantidadeOitivasAte);
    }

    if (audienciafilter.duracaoEstimadaDe) {
      params.set('duracaoEstimadaDe', audienciafilter.duracaoEstimadaDe);
    }

    if (audienciafilter.duracaoEstimadaAte) {
      params.set('duracaoEstimadaAte', audienciafilter.duracaoEstimadaAte);
    }

    if (audienciafilter.observacao) {
      params.set('observacao', audienciafilter.observacao);
    }

    if (audienciafilter.numeroProcesso) {
      params.set('numeroProcesso', audienciafilter.numeroProcesso);
    }

    if (audienciafilter.nomeDaParteProcesso) {
      params.set('nomeDaParteProcesso', audienciafilter.nomeDaParteProcesso);
    }

    return this.http.get(`${this.audienciasUrl}/filtrar`,
        { search: params }).toPromise().then(response => {
          const responseJson = response.json();
          const audiencias = responseJson.content;

          const resultado = {
            audiencias: audiencias,
        };

        return resultado;
      });
  }

  filtrarConciliacao(conciliacaoFilter: ConciliacaoFilter): Promise<any> {

    const params = new URLSearchParams();

    if (conciliacaoFilter.dataAgendamentoDe) {
      params.set('dataAgendamentoDe', conciliacaoFilter.dataAgendamentoDe);
    }

    if (conciliacaoFilter.dataAgendamentoAte) {
      params.set('dataAgendamentoAte', conciliacaoFilter.dataAgendamentoAte);
    }

    if (conciliacaoFilter.statusAgendamento) {
      params.set('statusAgendamento', conciliacaoFilter.statusAgendamento);
    }

    if (conciliacaoFilter.nomeConciliador) {
      params.set('nomeConciliador', conciliacaoFilter.nomeConciliador);
    }

    if (conciliacaoFilter.quantidadeOitivasDe) {
      params.set('quantidadeOitivasDe', conciliacaoFilter.quantidadeOitivasDe);
    }

    if (conciliacaoFilter.quantidadeOitivasAte) {
      params.set('quantidadeOitivasAte', conciliacaoFilter.quantidadeOitivasAte);
    }

    if (conciliacaoFilter.duracaoEstimadaDe) {
      params.set('duracaoEstimadaDe', conciliacaoFilter.duracaoEstimadaDe);
    }

    if (conciliacaoFilter.duracaoEstimadaAte) {
      params.set('duracaoEstimadaAte', conciliacaoFilter.duracaoEstimadaAte);
    }

    if (conciliacaoFilter.observacao) {
      params.set('observacao', conciliacaoFilter.observacao);
    }

    if (conciliacaoFilter.numeroProcesso) {
      params.set('numeroProcesso', conciliacaoFilter.numeroProcesso);
    }

    if (conciliacaoFilter.nomeDaParteProcesso) {
      params.set('nomeDaParteProcesso', conciliacaoFilter.nomeDaParteProcesso);
    }

    return this.http.get(`${this.conciliacoesUrl}/filtrar`,
        { search: params }).toPromise().then(response => {
          const responseJson = response.json();
          const conciliacoes = responseJson.content;

          const resultado = {
            conciliacoes: conciliacoes,
        };

        return resultado;
      });
  }

}
