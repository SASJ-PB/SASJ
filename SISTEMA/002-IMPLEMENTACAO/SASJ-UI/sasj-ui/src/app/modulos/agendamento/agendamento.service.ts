import { AuthHttp } from 'angular2-jwt';
import { Audiencia, Conciliacao, Processo } from './../core/model';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class AgendamentoService {

  audienciasUrl = 'http://localhost:8080/audiencias';
  conciliacoesUrl = 'http://localhost:8080/conciliacoes';

  constructor(private http: AuthHttp) { }

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
    return this.http.put(`http://localhost:8080/processos/${processo.codigo}`,
        JSON.stringify(processo))
      .toPromise()
      .then(response => {
        const processoAlterado = response.json() as Processo;
        return processoAlterado;
      });
  }

}
