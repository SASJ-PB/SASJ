import { Conciliacao, Audiencia, Processo } from './../../core/model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorHandlerService } from './../../core/error-handler.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AgendamentoService } from './../agendamento.service';
import { ParteInteressada } from './../agendamento-detalhes/agendamento-detalhes.component';
import { MatSort } from '@angular/material/sort';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-agendamento-cadastro',
  templateUrl: './agendamento-cadastro.component.html',
  styleUrls: ['./agendamento-cadastro.component.css']
})
export class AgendamentoCadastroComponent implements OnInit {

  @ViewChild(MatSort) sort: MatSort;

  formulario: FormGroup;
  campoData: FormControl;
  campoHora: FormControl;
  campoNumeroProcesso: FormControl;
  campoNomeParte: FormControl;
  campoQuantidadeOitivas: FormControl;
  // campoTipoAudiencia: FormControl;
  campoNomeConciliador: FormControl;
  campoTempoDuracao: FormControl;
  campoTipoSessao: FormControl;
  campoDataLembrete: FormControl;
  campoHoraLembrete: FormControl;
  campoObservacao: FormControl;

  mascaraCamposNumericos = [/[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/];

  public mascaraData = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/];
  public mascaraHora = [/\d/, /\d/, ':', /\d/, /\d/];

  tempoDuracaoAudienciaEscolhida = 0;
  tipoAudiencia = 'Ação civíl';
  tempoDuracao = 1;
  isEdicao = false;
  dataSource: MatTableDataSource<ParteInteressada>;
  colunasExibidas = ['nome', 'email', 'papel']; // 'acoes'
  conciliacao = new Conciliacao();
  audiencia = new Audiencia();
  processo = new Processo();

  tiposAudiencias = [
    {nome: 'Ação civíl', duracao: 20}, {nome: 'Improbidade', duracao: 20},
    {nome: 'Instrução do creta', duracao: 7}, {nome: 'Leilão', duracao: 60},
    {nome: 'Outros', duracao: 20}, {nome: 'Penal', duracao: 20}, {nome: 'PJE', duracao: 20},
    {nome: 'Tebas improbidade', duracao: 20}, {nome: 'Videoconferência', duracao: 20}
  ];

  constructor(private agendamentoService: AgendamentoService, private route: ActivatedRoute,
      private snackBar: MatSnackBar, private router: Router,
      private errorHandlerService: ErrorHandlerService) {

    const codigoAgendamento = this.route.snapshot.params['codigo'];

    if (codigoAgendamento){
      this.carregarAgendamento(codigoAgendamento);
      this.isEdicao = true;
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
    this.campoNumeroProcesso = new FormControl('', [Validators.required]); // {value: '', disabled: this.isEdicao}
    this.campoNomeParte = new FormControl('', [Validators.required]); // {value: '', disabled: this.isEdicao}

    this.campoQuantidadeOitivas = new FormControl(1, [Validators.required]);
    this.campoTempoDuracao = new FormControl('', [Validators.required]);
    this.campoNomeConciliador = new FormControl('', [Validators.required]);
    this.campoTipoSessao = new FormControl({value: 'Audiência', disabled: this.isEdicao}, [Validators.required]);

    this.setTempoDuracao(20, 1);
    this.recalcularTempoDuracao(1);
  }

  setTempoDuracao(duracaoAudiencia: number, oitivas: number) {

    if (duracaoAudiencia === 0){
      const audienciaFiltrada = this.tiposAudiencias.filter(resultado =>
        resultado.nome === this.tipoAudiencia);

      if (audienciaFiltrada.length !== 0) {
        duracaoAudiencia = audienciaFiltrada[0].duracao;
        this.tempoDuracao = (duracaoAudiencia * oitivas); // / 60
      }
    }

    else if (this.campoTipoSessao.value !== 'Audiência') {
      this.tempoDuracao = (duracaoAudiencia * oitivas); // / 60
    }
    else{
      this.tempoDuracao = (20 * oitivas);
    }
    this.tempoDuracaoAudienciaEscolhida = duracaoAudiencia;
  }

  // chamado quando o usuário muda a quantidade de oitivas, após escolher o tipo de audiência
  recalcularTempoDuracao(oitivas: number) {

    let tempoDuracao;

    if (this.campoTipoSessao.value !== 'Conciliação'){
      tempoDuracao = (this.tempoDuracaoAudienciaEscolhida * oitivas);
    }
    else{
      tempoDuracao = (20 * oitivas);
    }

    this.campoTempoDuracao.setValue(tempoDuracao);
  }

  limparCampos() {
    this.campoQuantidadeOitivas.setValue('');
    this.tipoAudiencia = '';
    this.campoNomeConciliador.setValue('');
    this.campoTempoDuracao.setValue('');
    this.tempoDuracaoAudienciaEscolhida = 0;
  }

  cadastrar(){

    this.processo.numeroProcesso = this.campoNumeroProcesso.value;
    this.processo.nomeDaParte = this.campoNomeParte.value;

    if (this.campoTipoSessao.value === 'Audiência'){
      this.audiencia.processo = this.processo;
      this.audiencia.duracaoEstimada = this.campoTempoDuracao.value;
      this.audiencia.observacao = this.campoObservacao.value;
      this.audiencia.quantidadeOitivas = this.campoQuantidadeOitivas.value;
      this.audiencia.agendamento = this.formatarDataHora();

      this.audiencia.tipoAudiencia =
          this.converterTipoAudienciaLabelParaEnum(this.tipoAudiencia);

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
      this.conciliacao.agendamento = this.formatarDataHora();

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
    // this.audiencia.processo.nomeDaParte = this.campoNomeParte.value;
    // this.audiencia.processo.numeroProcesso = this.campoNumeroProcesso.value;
    this.audiencia.quantidadeOitivas = this.campoQuantidadeOitivas.value;
    this.audiencia.tipoAudiencia = this.converterTipoAudienciaLabelParaEnum(this.tipoAudiencia);
    this.audiencia.agendamento = this.formatarDataHora();

    this.atualizarProcesso();

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
    this.conciliacao.agendamento = this.formatarDataHora();

    this.atualizarProcesso();

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

  private atualizarProcesso() {
    this.processo.numeroProcesso = this.campoNumeroProcesso.value;
    this.processo.nomeDaParte = this.campoNomeParte.value;

    this.agendamentoService.atualizarProcesso(this.processo)
      .then(processo => {
        this.processo = processo;
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
          this.processo = audiencia.processo;

          this.campoTipoSessao.setValue('Audiência');
          this.preencherDataHoraAudiencia(this.audiencia);
          this.campoNumeroProcesso.setValue(this.audiencia.processo.numeroProcesso);
          this.campoNomeParte.setValue(this.audiencia.processo.nomeDaParte);
          this.campoTempoDuracao.setValue(this.audiencia.duracaoEstimada);
          this.campoObservacao.setValue(this.audiencia.observacao);
          this.campoQuantidadeOitivas.setValue(this.audiencia.quantidadeOitivas);

          const valorTipoAudiencia = this.converterTipoAudienciaEnumParaLabel(this.audiencia.tipoAudiencia);

          this.tipoAudiencia = valorTipoAudiencia;

          const audienciaFiltrada = this.tiposAudiencias.filter(resultado =>
            resultado.nome === this.tipoAudiencia);

          this.tempoDuracaoAudienciaEscolhida = audienciaFiltrada[0].duracao;
        })
        .catch(erro => this.errorHandlerService.handle(erro));
    }
    else{

      this.agendamentoService.buscarConciliacaoPorCodigo(codigo)
        .then(conciliacao => {
          this.conciliacao = conciliacao;
          this.processo = conciliacao.processo;

          this.campoTipoSessao.setValue('Conciliação');
          this.preencherDataHoraConciliacao(this.conciliacao);
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
  //            para cadastro            [0] [1] [2]
  private formatarDataHora(): string{ // aaaa-MM-dd HH:mm

    const valorCampoData: string = this.campoData.value;

    const data = valorCampoData.split('/');

    return data[2] + '-' + data[1] + '-' + data[0] + ' ' + this.campoHora.value;
  }

  private preencherDataHoraAudiencia(audiencia: Audiencia) {

    const dataHora = audiencia.agendamento.split(' ');

    const data = dataHora[0];
    const hora = dataHora[1];

    const arrayData = data.split('-');

    // const dataFormatada = arrayData[2] + '/' + arrayData[1] + '/' + arrayData[0];

    this.campoData.setValue(arrayData[2] + '/' + arrayData[1] + '/' + arrayData[0]);
    this.campoHora.setValue(hora);
  }

  private preencherDataHoraConciliacao(conciliacao: Conciliacao) {

    const dataHora = conciliacao.agendamento.split(' ');

    const data = dataHora[0];
    const hora = dataHora[1];

    const arrayData = data.split('-');

    // const dataFormatada = arrayData[2] + '/' + arrayData[1] + '/' + arrayData[0];

    this.campoData.setValue(arrayData[2] + '/' + arrayData[1] + '/' + arrayData[0]);
    this.campoHora.setValue(hora);
  }

  isAudiencia(): boolean{
    if (this.campoTipoSessao.value === 'Audiência'){ // this.tipoSessaoEscolhido === 'Audiência' ||
      return true;
    }
    return false;
  }

  private converterTipoAudienciaLabelParaEnum(tipoAudiencia: any): string{
    if (tipoAudiencia === 'Ação civíl'){
      return 'ACAO_CIVIL';
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

  private converterTipoAudienciaEnumParaLabel(tipoAudiencia: string): string{
    if (tipoAudiencia === 'ACAO_CIVIL'){
      return 'Ação civíl';
    }
    else if (tipoAudiencia === 'IMPROBIDADE'){
      return 'Improbidade';
    }
    else if (tipoAudiencia === 'INSTRUCAO_CRETA'){
      return 'Instrução do creta';
    }
    else if (tipoAudiencia === 'LEILAO'){
      return 'Leilão';
    }
    else if (tipoAudiencia === 'OUTROS'){
      return 'Outros';
    }
    else if (tipoAudiencia === 'PENAL'){
      return 'Penal';
    }
    else if (tipoAudiencia === 'PJE'){
      return 'PJE';
    }
    else if (tipoAudiencia === 'TEBAS_IMPROBIDADE'){
      return 'Tebas improbidade';
    }
    else {
      return 'Videoconferência';
    }
  }

}
