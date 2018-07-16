import { environment } from './../../../environments/environment';
import { Injectable } from '@angular/core';
import { AuthHttp } from 'angular2-jwt';

@Injectable()
export class RelatorioService {

  relatorioUrl: string; // = 'relatorio/quantidadeTipoAudiencia';

  constructor(private http: AuthHttp) {
    this.relatorioUrl = `${environment.apiUrl}/relatorio`;
  }

  listarQtdAudienciasPorTipo(dataDe: string, dataAte: string): Promise<any> {

    const params = new URLSearchParams();

    params.set('dataDe', dataDe);
    params.set('dataAte', dataAte);

    return this.http.get(`${this.relatorioUrl}/quantidadeTipoAudiencia`, { search: params })
      .toPromise()
      .then(response => {

        const responseJson = response.json();
          const qtdAudiencias = responseJson.content;

          const resultado = {
            qtdAudiencias: qtdAudiencias,
          };

        return resultado;
      });

  }
}
