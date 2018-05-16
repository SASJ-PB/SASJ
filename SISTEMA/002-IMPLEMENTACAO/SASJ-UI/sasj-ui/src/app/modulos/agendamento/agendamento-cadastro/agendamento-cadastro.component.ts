import { ParteInteressada } from './../agendamento-detalhes/agendamento-detalhes.component';
import { MatSort } from '@angular/material/sort';
import { FormControl, Validators } from '@angular/forms';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-agendamento-cadastro',
  templateUrl: './agendamento-cadastro.component.html',
  styleUrls: ['./agendamento-cadastro.component.css']
})
export class AgendamentoCadastroComponent implements OnInit {

  @ViewChild(MatSort) sort: MatSort;

  campoData: FormControl;
  campoHora: FormControl;
  campoDataLembrete: FormControl;
  campoHoraLembrete: FormControl;
  campoQuantidadeOitivas: FormControl;

  public mascaraData = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/];
  public mascaraHora = [/\d/, /\d/, ':', /\d/, /\d/];

  tempoDuracaoAudienciaEscolhida = 0;
  tipoSessaoEscolhido = 'Audiência';
  tempoDuracao = 0;
  tiposSessoes = ['Audiência', 'Conciliação'];
  dataSource: MatTableDataSource<ParteInteressada>;
  colunasExibidas = ['nome', 'email', 'papel']; // 'acoes'

  tiposAudiencias = [
    {nome: 'Ação civíl', duracao: 20}, {nome: 'Improbidade', duracao: 20},
    {nome: 'Instrução do creta', duracao: 7}, {nome: 'Leilão', duracao: 60},
    {nome: 'Outros', duracao: 20}, {nome: 'Penal', duracao: 20}, {nome: 'PJE', duracao: 20},
    {nome: 'Tebas improbidade', duracao: 20}, {nome: 'Vídeo-conferência', duracao: 20}
  ];

  constructor() {
    const dadosPartesInteressadas: ParteInteressada[] = [
      {nome: 'João', email: 'joao@gmail.com', papel: 'Juíz'},
      {nome: 'Maria', email: 'maria@outlook.com', papel: 'Advogada de defesa'},
      {nome: 'Rita', email: 'rita@yahoo.com', papel: 'Ré'},
    ];

    this.dataSource = new MatTableDataSource(dadosPartesInteressadas);
  }

  ngOnInit() {
    this.campoData = new FormControl('', [Validators.required]);
    this.campoHora = new FormControl('', [Validators.required]);

    this.campoDataLembrete = new FormControl();
    this.campoHoraLembrete = new FormControl();
    this.campoQuantidadeOitivas = new FormControl();
  }

  setTempoDuracao(duracaoAudiencia: number, oitivas: number) {
    this.tempoDuracao = duracaoAudiencia * oitivas;
    this.tempoDuracaoAudienciaEscolhida = duracaoAudiencia;
  }

  // chamado quando o usuário muda a quantidade de oitivas, após escolher o tipo de audiência
  recalcularTempoDuracao(oitivas: number) {
    if (this.tempoDuracaoAudienciaEscolhida !== 0){
      this.tempoDuracao = this.tempoDuracaoAudienciaEscolhida * oitivas;
    }
  }
}
