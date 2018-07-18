import { environment } from './../../../environments/environment';
import { Injectable } from '@angular/core';
import { AuthHttp } from 'angular2-jwt';
import { URLSearchParams } from '@angular/http';


@Injectable()
export class RelatorioService {

  relatorioUrl: string; // = 'relatorio/quantidadeTipoAudiencia';

  constructor(private http: AuthHttp) {
    this.relatorioUrl = `${environment.apiUrl}/relatorio`;
  }

  listarQtdAudienciasPorTipo(dataDe: string, dataAte: string): Promise<any> {

    const params = new URLSearchParams();

    params.set('de', dataDe);
    params.set('ate', dataAte);

    return this.http.get(`${this.relatorioUrl}/quantidadeTipoAudiencia`, { search: params })
      .toPromise()
      .then(response => {
        const responseJson = response.json();

        return responseJson;
      });
  }

  listarQtdOitivasPorTipoAudiencia(dataDe: string, dataAte: string): Promise<any> {

    const params = new URLSearchParams();

    params.set('de', dataDe);
    params.set('ate', dataAte);

    return this.http.get(`${this.relatorioUrl}/quantidadeOitivas`, { search: params })
      .toPromise()
      .then(response => {
        const responseJson = response.json();

        return responseJson;
      });
  }

  listarQtdHorasPorTipoAudiencia(dataDe: string, dataAte: string): Promise<any> {

    const params = new URLSearchParams();

    params.set('de', dataDe);
    params.set('ate', dataAte);

    return this.http.get(`${this.relatorioUrl}/quantidadeHoras`, { search: params })
      .toPromise()
      .then(response => {
        const responseJson = response.json();

        return responseJson;
      });
  }

  listarQtdConciliacoesPorConciliador(dataDe: string, dataAte: string): Promise<any> {

    const params = new URLSearchParams();

    params.set('de', dataDe);
    params.set('ate', dataAte);

    return this.http.get(`${this.relatorioUrl}/quantidadeConciliacoes`, { search: params })
      .toPromise()
      .then(response => {
        const responseJson = response.json();

        return responseJson;
      });
  }
}
