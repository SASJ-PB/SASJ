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

  public mascaraData = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/];
  public mascaraHora = [/\d/, /\d/, ':', /\d/, /\d/];

  tiposSessoes = ['Audiência', 'Conciliação'];
  tiposAudiencias = [
    'Ação civíl', 'Improbidade com vídeo', 'Instrução do creta',
    'Leilão', 'Outros', 'Penal com vídeo', 'PJE',
    'Tebas improbidade', 'Vídeo-conferência'
  ];

  dataSource: MatTableDataSource<ParteInteressada>;

  colunasExibidas = ['nome', 'email', 'papel']; // 'acoes'

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
  }

}
