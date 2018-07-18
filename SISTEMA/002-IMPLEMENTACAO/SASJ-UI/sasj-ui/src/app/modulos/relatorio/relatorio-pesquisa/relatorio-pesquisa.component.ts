import { AbstractControl, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { RelatorioService } from './../relatorio.service';
import { ErrorHandlerService } from '../../core/error-handler.service';

@Component({
  selector: 'app-relatorio-pesquisa',
  templateUrl: './relatorio-pesquisa.component.html',
  styleUrls: ['./relatorio-pesquisa.component.css']
})
export class RelatorioPesquisaComponent implements OnInit {

  dataGraficoA: any;
  dataGraficoB: any;
  dataGraficoC: any;
  dataGraficoD: any;

  optionsGraficoA: any;
  optionsGraficoB: any;
  optionsGraficoC: any;
  optionsGraficoD: any;

  campoDataInicio: FormControl;
  campoDataFinal: FormControl;

  exibirGraficos = false;
  exibirGraficoA = false;
  exibirGraficoB = false;
  exibirGraficoC = false;

  public mascaraData = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/];

  constructor(private relatorioService: RelatorioService,
      private errorHandlerService: ErrorHandlerService) {}

  ngOnInit() {
    this.campoDataInicio = new FormControl('', [Validators.required, Validators.minLength(10), ValidateDate]);
    this.campoDataFinal = new FormControl('', [Validators.required, Validators.minLength(10), ValidateDate]);
  }

  gerarEstatisticas()
  {
    const valorDataDe = this.formatarDataDe(this.campoDataInicio.value);
    const valorDataAte = this.formatarDataAte(this.campoDataFinal.value);

    this.gerarGraficoAudiencias(valorDataDe, valorDataAte);
    this.gerarGraficoOitivas(valorDataDe, valorDataAte);
    this.gerarGraficoTempoDeUsoSala(valorDataDe, valorDataAte);
    this.gerarGraficoConciliadores(valorDataDe, valorDataAte);

    this.exibirGraficos = true;
  }

  gerarGraficoAudiencias(dataDe: string, dataAte: string)
  {
    this.relatorioService.listarQtdAudienciasPorTipo(dataDe, dataAte)
      .then(resultado => {

        if (this.isObjetoVazio(resultado)){
          this.exibirGraficoA = false;
        }
        else{
          this.exibirGraficoA = true;
        }

        this.dataGraficoA = {
          labels: ['AÇÃO CIVIL', 'CUSTÓDIA', 'IMPROBIDADE', 'INSTRUÇÃO DO CRETA',
            'LEILÃO', 'OUTROS', 'PENAL', 'PJE', 'TEBAS IMPROBIDADE', 'VIDEOCONFERÊNCIA'
          ],
          datasets: [
            {
              data:
                [
                  resultado.qtdAcaoCivil, resultado.qtdCustodia, resultado.qtdImprobidade,
                  resultado.qtdInstrucaoCreta, resultado.qtdLeilao, resultado.qtdOutros,
                  resultado.qtdPenal, resultado.qtdPJE, resultado.qtdTebasImprobidade,
                  resultado.qtdVideoconferencia
                ],
              backgroundColor: [
                '#f44336',
                '#9c27b0',
                '#FFCE56',
                '#3f51b5',
                '#03a9f4',
                '#009688',
                '#8bc34a',
                '#ffeb3b',
                '#ff9800',
                '#795548',
              ],
              hoverBackgroundColor: [
                '#f44336',
                '#9c27b0',
                '#FFCE56',
                '#3f51b5',
                '#03a9f4',
                '#009688',
                '#8bc34a',
                '#ffeb3b',
                '#ff9800',
                '#795548',
              ]
            }
          ]
        };

        this.optionsGraficoA = {
          responsive: true,
          title: {
            display: true,
            text: 'QUANTIDADE DE AUDIÊNCIAS POR TIPO DE AUDIÊNCIA',
            fontSize: 20
          },
          legend: {
            position: 'right'
          }
        };
    })
    .catch(erro => this.errorHandlerService.handle(erro));
  }

  gerarGraficoOitivas(dataDe: string, dataAte: string)
  {
    this.relatorioService.listarQtdOitivasPorTipoAudiencia(dataDe, dataAte)
      .then(resultado => {

        if (this.isObjetoVazio(resultado)){
          this.exibirGraficoB = false;
        }
        else{
          this.exibirGraficoB = true;
        }

        this.dataGraficoB = {
          labels: ['AÇÃO CIVÍL', 'CUSTÓDIA', 'IMPROBIDADE', 'INSTRUÇÃO DO CRETA',
            'LEILÃO', 'OUTROS', 'PENAL', 'PJE', 'TEBAS IMPROBIDADE', 'VIDEOCONFERÊNCIA'
          ],
          datasets: [
            {
              data: [
                resultado.qtdAcaoCivil, resultado.qtdCustodia, resultado.qtdImprobidade,
                resultado.qtdInstrucaoCreta, resultado.qtdLeilao, resultado.qtdOutros,
                resultado.qtdPenal, resultado.qtdPJE, resultado.qtdTebasImprobidade,
                resultado.qtdVideoconferencia
              ],
              backgroundColor: [
                '#f44336',
                '#9c27b0',
                '#FFCE56',
                '#3f51b5',
                '#03a9f4',
                '#009688',
                '#8bc34a',
                '#ffeb3b',
                '#ff9800',
                '#795548',
              ],
              hoverBackgroundColor: [
                '#f44336',
                '#9c27b0',
                '#FFCE56',
                '#3f51b5',
                '#03a9f4',
                '#009688',
                '#8bc34a',
                '#ffeb3b',
                '#ff9800',
                '#795548',
              ]
            }
          ]
        };

        this.optionsGraficoB = {
          title: {
            display: true,
            text: 'QUANTIDADE DE OITIVAS POR TIPO DE AUDIÊNCIA',
            fontSize: 20
          },
          legend: {
            position: 'right'
          }
        };
      })
      .catch(erro => this.errorHandlerService.handle(erro));
  }

  gerarGraficoTempoDeUsoSala(dataDe: string, dataAte: string)
  {
    this.relatorioService.listarQtdHorasPorTipoAudiencia(dataDe, dataAte)
      .then(resultado => {

        if (this.isObjetoVazio(resultado)){
          this.exibirGraficoC = false;
        }
        else{
          this.exibirGraficoC = true;
        }

        // const tempoFormatado: Array<string> = this.formatarTempo(resultado);

        this.dataGraficoC = {
          labels: ['AÇÃO CIVÍL', 'CUSTÓDIA', 'IMPROBIDADE', 'INSTRUÇÃO DO CRETA',
            'LEILÃO', 'OUTROS', 'PENAL', 'PJE', 'TEBAS IMPROBIDADE', 'VIDEOCONFERÊNCIA'
          ],
          datasets: [
            {
              data: [
                resultado.qtdAcaoCivil, resultado.qtdCustodia, resultado.qtdImprobidade,
                resultado.qtdInstrucaoCreta, resultado.qtdLeilao, resultado.qtdOutros,
                resultado.qtdPenal, resultado.qtdPJE, resultado.qtdTebasImprobidade,
                resultado.qtdVideoconferencia
              ],
              backgroundColor: [
                '#f44336',
                '#9c27b0',
                '#FFCE56',
                '#3f51b5',
                '#03a9f4',
                '#009688',
                '#8bc34a',
                '#ffeb3b',
                '#ff9800',
                '#795548',
              ],
              hoverBackgroundColor: [
                '#f44336',
                '#9c27b0',
                '#FFCE56',
                '#3f51b5',
                '#03a9f4',
                '#009688',
                '#8bc34a',
                '#ffeb3b',
                '#ff9800',
                '#795548',
              ]
            }
          ]
        };

        this.optionsGraficoC = {
          title: {
            display: true,
            text: 'TEMPO DE USO DA SALA POR TIPO DE AUDIÊNCIA (MINUTOS)',
            fontSize: 20
          },
          legend: {
            position: 'right'
          }
        };
      })
      .catch(erro => this.errorHandlerService.handle(erro));
  }

  gerarGraficoConciliadores(dataDe: string, dataAte: string)
  {
    this.relatorioService.listarQtdConciliacoesPorConciliador(dataDe, dataAte)
      .then(resultado => {

        const conciliadores = new Array();
        const quantConciliacoes = new Array();

        resultado.conciliacoesPorConciliador.forEach(element => {
          conciliadores.push(element.nomeConciliador);
          quantConciliacoes.push(element.numeroConciliacoes);
        });

        this.dataGraficoD = {
          labels: conciliadores,
          datasets: [
            {
              data: quantConciliacoes,
              backgroundColor: [
                '#f44336',
                '#9c27b0',
                '#FFCE56',
                '#3f51b5',
                '#03a9f4',
                '#009688',
                '#8bc34a',
                '#ffeb3b',
                '#ff9800',
                '#795548',
              ],
              hoverBackgroundColor: [
                '#f44336',
                '#9c27b0',
                '#FFCE56',
                '#3f51b5',
                '#03a9f4',
                '#009688',
                '#8bc34a',
                '#ffeb3b',
                '#ff9800',
                '#795548',
              ]
            }
          ]
        };

        this.optionsGraficoD = {
          title: {
            display: true,
            text: 'QUANTIDADE DE CONCILIAÇÕES REALIZADAS POR CADA CONCILIADOR',
            fontSize: 20
          },
          legend: {
            position: 'right'
          }
        };
      })
      .catch(erro => this.errorHandlerService.handle(erro));
  }

  private isObjetoVazio(objeto: any)
  {
    const array = new Array();

    if (objeto.qtdAcaoCivil !== 0) {
      array.push(objeto.qtdAcaoCivil);
    }
    if (objeto.qtdCustodia !== 0) {
      array.push(objeto.qtdCustodia);
    }
    if (objeto.qtdImprobidade !== 0) {
      array.push(objeto.qtdImprobidade);
    }
    if (objeto.qtdInstrucaoCreta !== 0) {
      array.push(objeto.qtdInstrucaoCreta);
    }
    if (objeto.qtdLeilao !== 0) {
      array.push(objeto.qtdLeilao);
    }
    if (objeto.qtdOutros !== 0) {
      array.push(objeto.qtdOutros);
    }
    if (objeto.qtdPenal !== 0) {
      array.push(objeto.qtdPenal);
    }
    if (objeto.qtdPJE !== 0) {
      array.push(objeto.qtdPJE);
    }
    if (objeto.qtdTebasImprobidade !== 0) {
      array.push(objeto.qtdTebasImprobidade);
    }
    if (objeto.qtdVideoconferencia !== 0) {
      array.push(objeto.qtdVideoconferencia);
    }

    return array.length === 0;
  }

  private formatarDataDe(data: string): string
  { // aaaa-MM-dd HH:mm
    const dataFormatada = data.split('/');

    return dataFormatada[2] + '-' + dataFormatada[1] + '-' + dataFormatada[0] + ' ' + '00:00';
  }

  private formatarDataAte(data: string): string
  { // aaaa-MM-dd HH:mm
    const dataFormatada = data.split('/');

    return dataFormatada[2] + '-' + dataFormatada[1] + '-' + dataFormatada[0] + ' ' + '23:59';
  }

}

export function ValidateDate(control: AbstractControl){
  // TENTAR CADASTRAR UM AGENDAMENTO NO MES 12
  const valorCampoData = control.value;

  const data = valorCampoData.split('/');

  const dia: number = data[0];
  const mes: number = data[1];
  const ano: number = data[2];

  if (dia > 31 || dia < 1){
    return { validDate: true};
  }
  if (mes > 12 || mes < 1){
    return { validDate: true};
  }
  if ((mes === 4 || mes === 6 || mes === 9 || mes === 11) && dia === 31) {
    return { validDate: true};
  }
  if (mes === 2) { // check for february 29th

    const isleap = (ano % 4 === 0 && (ano % 100 !== 0 || ano % 400 === 0));

    if (dia > 29 || (dia === 29 && !isleap)) {
        return { validDate: true};
    }
  }

  return null;
}
