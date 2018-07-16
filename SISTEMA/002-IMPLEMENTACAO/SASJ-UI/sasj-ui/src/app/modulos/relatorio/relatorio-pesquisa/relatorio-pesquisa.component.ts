import { AbstractControl, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { RelatorioService } from './../relatorio.service';

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

  public mascaraData = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/];

  constructor(private relatorioService: RelatorioService) {}

  ngOnInit() {
    this.campoDataInicio = new FormControl('', [Validators.required, Validators.minLength(10), ValidateDate]);
    this.campoDataFinal = new FormControl('', [Validators.required, Validators.minLength(10), ValidateDate]);
  }

  gerarEstatisticas() {

    const valorDataDe = this.formatarDataDe(this.campoDataInicio.value);
    const valorDataAte = this.formatarDataAte(this.campoDataFinal.value);

    this.gerarGraficoAudiencias(valorDataDe, valorDataAte);
    // this.gerarGraficoOitivas();
    // this.gerarGraficoTempoDeUsoSala();
    // this.gerarGraficoConciliadores();
  }

  gerarGraficoAudiencias(dataDe: string, dataAte: string) {

    const respostaJson = this.relatorioService.listarQtdAudienciasPorTipo(dataDe, dataAte);

    console.log(respostaJson);

    /*this.dataGraficoA = {
      labels: ['AÇÃO CIVIL', 'CUSTÓDIA', 'IMPROBIDADE', 'INSTRUÇÃO DO CRETA',
        'LEILÃO', 'OUTROS', 'PENAL', 'PJE', 'TEBAS IMPROBIDADE', 'VIDEOCONFERÊNCIA'
      ],
      datasets: [
        {
          data: [300, 50, 100, 50, 20, 10, 203, 150, 200, 120],
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
    };*/
  }

  gerarGraficoOitivas() {
    this.dataGraficoB = {
      labels: ['AÇÃO CIVÍL', 'CUSTÓDIA', 'IMPROBIDADE', 'INSTRUÇÃO DO CRETA',
        'LEILÃO', 'OUTROS', 'PENAL', 'PJE', 'TEBAS IMPROBIDADE', 'VIDEOCONFERÊNCIA'
      ],
      datasets: [
        {
          data: [300, 50, 100, 50, 20, 10, 203, 150, 200, 120],
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

  }

  gerarGraficoTempoDeUsoSala() {
    this.dataGraficoC = {
      labels: ['AÇÃO CIVÍL', 'CUSTÓDIA', 'IMPROBIDADE', 'INSTRUÇÃO DO CRETA',
        'LEILÃO', 'OUTROS', 'PENAL', 'PJE', 'TEBAS IMPROBIDADE', 'VIDEOCONFERÊNCIA'
      ],
      datasets: [
        {
          data: [300, 50, 100, 50, 20, 10, 203, 150, 200, 120],
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
        text: 'TEMPO DE USO DA SALA POR TIPO DE AUDIÊNCIA',
        fontSize: 20
      },
      legend: {
        position: 'right'
      }
    };
  }

  gerarGraficoConciliadores() {
    this.dataGraficoD = {
      labels: ['DANIELE', 'EWERTON', 'FRANCIMÁRIA', 'JÚLIO CESAR',
        'MARIA SILVANA', 'MAX', 'RAYANA'
      ],
      datasets: [
        {
          data: [130, 52, 112, 150, 192, 148, 70],
          backgroundColor: [
            '#f44336',
            '#9c27b0',
            '#FFCE56',
            '#3f51b5',
            '#03a9f4',
            '#009688',
            '#8bc34a',
          ],
          hoverBackgroundColor: [
            '#f44336',
            '#9c27b0',
            '#FFCE56',
            '#3f51b5',
            '#03a9f4',
            '#009688',
            '#8bc34a',
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

  }

  private formatarDataDe(data: string): string{ // aaaa-MM-dd HH:mm

    const dataFormatada = data.split('/');

    return dataFormatada[2] + '-' + dataFormatada[1] + '-' + dataFormatada[0] + ' ' + '00:00';
  }

  private formatarDataAte(data: string): string{ // aaaa-MM-dd HH:mm

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
