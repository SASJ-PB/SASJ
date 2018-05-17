import { Conciliacao, Audiencia, Processo } from './../../core/model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorHandlerService } from './../../core/error-handler.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AgendamentoService } from './../agendamento.service';
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
  campoNumeroProcesso: FormControl;
  campoNomeParte: FormControl;
  campoQuantidadeOitivas: FormControl;
  campoTipoAudiencia: FormControl;
  campoNomeConciliador: FormControl;
  campoTempoDuracao: FormControl;
  campoTipoSessao: FormControl;
  campoDataLembrete: FormControl;
  campoHoraLembrete: FormControl;
  campoObservacao: FormControl;
  isEdicao = false;

  public mascaraData = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/];
  public mascaraHora = [/\d/, /\d/, ':', /\d/, /\d/];

  tempoDuracaoAudienciaEscolhida = 0;
  tipoSessaoEscolhido = 'Audiência';
  tempoDuracao = 0;
  tiposSessoes = ['Audiência', 'Conciliação'];
  dataSource: MatTableDataSource<ParteInteressada>;
  colunasExibidas = ['nome', 'email', 'papel']; // 'acoes'
  conciliacao = new Conciliacao();
  audiencia = new Audiencia();
  processo = new Processo();

  tiposAudiencias = [
    {nome: 'Ação civíl', duracao: 20}, {nome: 'Improbidade', duracao: 20},
    {nome: 'Instrução do creta', duracao: 7}, {nome: 'Leilão', duracao: 60},
    {nome: 'Outros', duracao: 20}, {nome: 'Penal', duracao: 20}, {nome: 'PJE', duracao: 20},
    {nome: 'Tebas improbidade', duracao: 20}, {nome: 'Vídeo-conferência', duracao: 20}
  ];

  constructor(private agendamentoService: AgendamentoService,
      private snackBar: MatSnackBar,
      private errorHandlerService: ErrorHandlerService,
      private router: Router, private route: ActivatedRoute) {

    const codigoAgendamento = this.route.snapshot.params['codigo'];

    if (codigoAgendamento){
      this.carregarAgendamento(codigoAgendamento);
    }

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

    this.campoObservacao = new FormControl();
    this.campoNumeroProcesso = new FormControl('', [Validators.required]);
    this.campoNomeParte = new FormControl('', [Validators.required]);

    this.campoQuantidadeOitivas = new FormControl('', [Validators.required]);
    this.campoTempoDuracao = new FormControl('', [Validators.required]);
    this.campoTipoSessao = new FormControl('', [Validators.required]);

    this.campoTipoAudiencia = new FormControl('', [Validators.required]);
    this.campoNomeConciliador = new FormControl('', [Validators.required]);

    const codigoAgendamento = this.route.snapshot.params['codigo'];

    if (codigoAgendamento) {
      this.carregarAgendamento(codigoAgendamento);
      this.isEdicao = true;
    }
  }

  setTempoDuracao(duracaoAudiencia: number, oitivas: number) {
    if (this.campoTipoSessao.value !== 'Conciliação') {
      this.tempoDuracao = (duracaoAudiencia * oitivas); // / 60
    }
    else{
      this.tempoDuracao = (20 * oitivas);
    }
    this.tempoDuracaoAudienciaEscolhida = duracaoAudiencia;
  }

  // chamado quando o usuário muda a quantidade de oitivas, após escolher o tipo de audiência
  recalcularTempoDuracao(oitivas: number) {
    if (this.tempoDuracaoAudienciaEscolhida !== 0 && this.campoTipoSessao.value !== 'Conciliação'){
      this.tempoDuracao = (this.tempoDuracaoAudienciaEscolhida * oitivas); // / 60
    }
    else{
      this.tempoDuracao = (20 * oitivas);
    }
  }

  limparOitivas() {
    this.campoTempoDuracao.setValue('');
  }

  cadastrar(){

    this.processo.numeroProcesso = this.campoNumeroProcesso.value;
    this.processo.nomeDaParte = this.campoNomeParte.value;

    if (this.campoTipoSessao.value === 'Audiência'){
      this.audiencia.processo = this.processo;
      this.audiencia.duracaoEstimada = this.campoTempoDuracao.value;
      this.audiencia.observacao = this.campoObservacao.value;
      this.audiencia.quantidadeOitivas = this.campoQuantidadeOitivas.value;

      if (this.campoTipoAudiencia.value === 'Ação civíl'){
        this.audiencia.tipoAudiencia = 'ACAO_CIVIL';
      }
      else if (this.campoTipoAudiencia.value === 'Improbidade'){
        this.audiencia.tipoAudiencia = 'IMPROBIDADE';
      }
      else if (this.campoTipoAudiencia.value === 'Instrução do creta'){
        this.audiencia.tipoAudiencia = 'INSTRUCAO_CRETA';
      }
      else if (this.campoTipoAudiencia.value === 'Leilão'){
        this.audiencia.tipoAudiencia = 'LEILAO';
      }
      else if (this.campoTipoAudiencia.value === 'Outros'){
        this.audiencia.tipoAudiencia = 'OUTROS';
      }
      else if (this.campoTipoAudiencia.value === 'Penal'){
        this.audiencia.tipoAudiencia = 'PENAL';
      }
      else if (this.campoTipoAudiencia.value === 'PJE'){
        this.audiencia.tipoAudiencia = 'PJE';
      }
      else if (this.campoTipoAudiencia.value === 'Tebas improbidade'){
        this.audiencia.tipoAudiencia = 'TEBAS_IMPROBIDADE';
      }
      else {
        this.audiencia.tipoAudiencia = 'VIDEOCONFERENCIA';
      }

      if (!this.isEdicao) {
        this.agendamentoService.cadastrarAudiencia(this.audiencia)
        .then((audienciaAdicionada) => {
          this.router.navigate(['/agendamentos']);
          this.snackBar.open('Audiência cadastrada com sucesso', '', { duration: 4500});
        })
        .catch(erro => {
          this.errorHandlerService.handle(erro);
        });
      }
      else{
        this.atualizarAudiencia();
      }

    }
    else{
      this.conciliacao.processo = this.processo;
      this.conciliacao.duracaoEstimada = this.campoTempoDuracao.value;
      this.conciliacao.observacao = this.campoObservacao.value;
      this.conciliacao.quantidadeOitivas = this.campoQuantidadeOitivas.value;
      this.conciliacao.nomeConciliador = this.campoNomeConciliador.value;

      if (!this.isEdicao) {
        this.agendamentoService.cadastrarConciliacao(this.conciliacao)
        .then((conciliacaoAdicionada) => {
          this.router.navigate(['/agendamentos']);
          this.snackBar.open('Conciliação cadastrada com sucesso', '', { duration: 4500});
        })
        .catch(erro => {
          this.errorHandlerService.handle(erro);
        });
      }
      else{
        this.atualizarConciliacao();
      }
    }
  }

  atualizarAudiencia() {

    this.audiencia.duracaoEstimada = this.campoTempoDuracao.value;
    this.audiencia.observacao = this.campoObservacao.value;
    this.audiencia.processo.nomeDaParte = this.campoNomeParte.value;
    this.audiencia.processo.numeroProcesso = this.campoNumeroProcesso.value;
    this.audiencia.quantidadeOitivas = this.campoQuantidadeOitivas.value;
    this.audiencia.tipoAudiencia = this.campoTipoAudiencia.value;

    this.agendamentoService.atualizarAudiencia(this.audiencia)
      .then(audiencia => {
        this.audiencia = audiencia;

        this.router.navigate(['/agendamentos']);

        this.snackBar.open('Audiência atualizada com sucesso!', '', {duration: 4500});
      })
      .catch(erro => {
        this.errorHandlerService.handle(erro);
      });
  }

  atualizarConciliacao() {

    this.conciliacao.duracaoEstimada = this.campoTempoDuracao.value;
    this.conciliacao.observacao = this.campoObservacao.value;
    this.conciliacao.quantidadeOitivas = this.campoQuantidadeOitivas.value;
    this.conciliacao.nomeConciliador = this.campoNomeConciliador.value;

    this.agendamentoService.atualizarConciliacao(this.conciliacao)
      .then(conciliacao => {
        this.conciliacao = conciliacao;

        this.router.navigate(['/agendamentos']);
        this.snackBar.open('Conciliacao atualizada com sucesso!', '', {duration: 4500});
      })
      .catch(erro => {
        this.errorHandlerService.handle(erro);
      });
  }

  carregarAgendamento(codigo: number) {

    if (this.route.snapshot.data.tipo === 'audiencia') {

      this.agendamentoService.buscarAudienciaPorCodigo(codigo)
        .then(audiencia => {
          this.audiencia = audiencia;

          this.campoTipoSessao.setValue('Audiência');

          this.campoNumeroProcesso.setValue(this.audiencia.processo.numeroProcesso);
          this.campoNomeParte.setValue(this.audiencia.processo.nomeDaParte);
          this.campoTempoDuracao.setValue(this.audiencia.duracaoEstimada);
          this.campoObservacao.setValue(this.audiencia.observacao);
          this.campoQuantidadeOitivas.setValue(this.audiencia.quantidadeOitivas);
          this.campoTipoAudiencia.setValue(this.audiencia.tipoAudiencia);
        })
        .catch(erro => this.errorHandlerService.handle(erro));
    }
    else{

      this.agendamentoService.buscarConciliacaoPorCodigo(codigo)
        .then(conciliacao => {
          this.conciliacao = conciliacao;

          this.campoNumeroProcesso.setValue(this.conciliacao.processo.numeroProcesso);
          this.campoNomeParte.setValue(this.conciliacao.processo.nomeDaParte);
          this.campoTempoDuracao.setValue(this.conciliacao.duracaoEstimada);
          this.campoObservacao.setValue(this.conciliacao.observacao);
          this.campoQuantidadeOitivas.setValue(this.conciliacao.quantidadeOitivas);
          this.campoNomeConciliador.setValue(this.conciliacao.nomeConciliador);
        })
        .catch(erro => this.errorHandlerService.handle(erro));
    }
  }

}
