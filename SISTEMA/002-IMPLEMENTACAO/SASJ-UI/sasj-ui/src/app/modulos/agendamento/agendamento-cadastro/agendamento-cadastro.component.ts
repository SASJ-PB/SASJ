import { Conciliacao, Audiencia, Processo } from './../../core/model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorHandlerService } from './../../core/error-handler.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AgendamentoService } from './../agendamento.service';
import { ParteInteressada } from './../agendamento-detalhes/agendamento-detalhes.component';
import { MatSort } from '@angular/material/sort';
import { FormControl, Validators, FormGroup, AbstractControl } from '@angular/forms';
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
  campoNomeConciliador: FormControl;
  campoTempoDuracao: FormControl;
  campoTipoSessao: FormControl;
  campoDataLembrete: FormControl;
  campoHoraLembrete: FormControl;
  campoObservacao: FormControl;

  public mascaraData = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/];
  public mascaraHora = [/\d/, /\d/, ':', /\d/, /\d/];

  tempoDuracaoAudienciaEscolhida = 0;
  tipoAudiencia = 'Ação civíl';
  tempoDuracao = 1;
  isEdicao = false;
  isEdicaoAgendamentoReservado = false;
  codigoAgendamentoReservado;

  //  dataSource: MatTableDataSource<ParteInteressada>;
  // colunasExibidas = ['nome', 'email', 'papel']; // 'acoes'
  conciliacao = new Conciliacao();
  audiencia = new Audiencia();
  processo = new Processo();

  tiposAudiencias = [
    {nome: 'Ação civíl', duracao: 20}, {nome: 'Custódia', duracao: 20}, {nome: 'Improbidade', duracao: 20},
    {nome: 'Instrução do creta', duracao: 7}, {nome: 'Leilão', duracao: 60},
    {nome: 'Outros', duracao: 20}, {nome: 'Penal', duracao: 20}, {nome: 'PJE', duracao: 20},
    {nome: 'Tebas improbidade', duracao: 20}, {nome: 'Videoconferência', duracao: 20}
  ];

  constructor(private agendamentoService: AgendamentoService, private activatedRoute: ActivatedRoute,
      private snackBar: MatSnackBar, private router: Router,
      private errorHandlerService: ErrorHandlerService) {

    const codigoAgendamento = this.activatedRoute.snapshot.params['codigo'];
    const agendamentoReservado = this.activatedRoute.snapshot.data.tipo === 'reservado';

    if (codigoAgendamento) {
      this.isEdicao = true;
      this.carregarAgendamento(codigoAgendamento);

      // if (agendamentoReservado) {
      //   this.isEdicaoReserva = true;
      // }
    }

    /*const dadosPartesInteressadas: ParteInteressada[] = [
      {nome: 'João', email: 'joao@gmail.com', papel: 'Juíz'},
      {nome: 'Maria', email: 'maria@outlook.com', papel: 'Advogada de defesa'},
      {nome: 'Rita', email: 'rita@yahoo.com', papel: 'Ré'},
    ];

    this.dataSource = new MatTableDataSource(dadosPartesInteressadas);*/
  }

  ngOnInit() {

    this.campoData = new FormControl('', [Validators.required, Validators.minLength(10), ValidateDate]);
    this.campoHora = new FormControl('', [Validators.minLength(5), ValidateHour]);
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
      this.tempoDuracao = (5 * oitivas);
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
      tempoDuracao = (5 * oitivas);
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

  private setDadosReserva() {
    this.campoNumeroProcesso.setValue(' ');
    this.campoNomeParte.setValue(' ');
    this.campoQuantidadeOitivas.setValue(1);
    // this.campoHora.setValue('00:00');

    if (this.campoTipoSessao.value === 'Audiência') {
      this.tipoAudiencia = 'Ação civíl';
      this.campoTempoDuracao.setValue(20); // 1440
    }
    else {
      this.campoNomeConciliador.setValue(' ');
      this.campoTempoDuracao.setValue(5); // 1440
    }
  }

  private isCadastroReserva(): boolean {
    if ((this.campoNumeroProcesso.value === '' && this.campoNomeParte.value === '') ||
        (this.campoNumeroProcesso.value === ' ' && this.campoNomeParte.value === ' ')) {

      return true;
    }

    return false;
  }

  cadastrar(){

    // if (this.campoNumeroProcesso.value === '' && this.campoNomeParte.value === '') {
    //   this.campoNumeroProcesso.setValue(' ');
    //   this.campoNomeParte.setValue(' ');
    //   this.campoQuantidadeOitivas.setValue(1);
    //   this.campoTempoDuracao.setValue(1440);
    //   this.campoHora.setValue('00:00');

    //   if (this.campoTipoSessao.value === 'Audiência') {
    //     this.tipoAudiencia = 'Ação civíl';
    //   }
    //   else {
    //     this.campoNomeConciliador.setValue(' ');
    //   }
    // }

    if (this.isCadastroReserva()) {
      this.setDadosReserva();
      this.processo.numeroProcesso = '-';
      this.processo.nomeDaParte = '-';
    }
    else{
      this.processo.numeroProcesso = this.campoNumeroProcesso.value;
      this.processo.nomeDaParte = this.campoNomeParte.value;
    }

    if (this.campoTipoSessao.value === 'Audiência'){
      if (this.tipoAudiencia !== '') {
        this.audiencia.tipoAudiencia =
            this.converterTipoAudienciaLabelParaEnum(this.tipoAudiencia);
      }
      else{
        this.audiencia.tipoAudiencia = null;
      }

      this.audiencia.processo = this.processo;

      if (!this.isCadastroReserva()) {
        this.audiencia.duracaoEstimada = this.campoTempoDuracao.value;
        this.audiencia.observacao = this.campoObservacao.value;
        this.audiencia.quantidadeOitivas = this.campoQuantidadeOitivas.value;
        this.audiencia.agendamento = this.formatarDataHora();
        this.audiencia.statusAgendamento = 'CONFIRMADO';
      }
      else {
        this.audiencia.duracaoEstimada = 1440;
        this.audiencia.quantidadeOitivas = 1;

        const valueFieldDate: string = this.campoData.value;
        const arrayDate = valueFieldDate.split('/');
        const date = arrayDate[2] + '-' + arrayDate[1] + '-' + arrayDate[0] + ' ' + '00:00';

        this.audiencia.agendamento = date;
        this.audiencia.statusAgendamento = 'CONFIRMADO';
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
        if (this.isEdicaoAgendamentoReservado) {
          this.agendamentoService.excluirAudiencia(this.codigoAgendamentoReservado).then(() => {

            this.agendamentoService.cadastrarAudiencia(this.audiencia)
              .then((audienciaAdicionada) => {

                this.router.navigate(['/agendamentos']);
                this.snackBar.open('Audiência cadastrada com sucesso', '', { duration: 4500});
              })
              .catch(erro => {
                this.errorHandlerService.handle(erro);
              }
            );
          });
        }
        else {
          this.atualizarAudiencia();
        }
      }
    }
    else{

      this.conciliacao.processo = this.processo;

      if (!this.isCadastroReserva()) {
        this.conciliacao.duracaoEstimada = this.campoTempoDuracao.value;
        this.conciliacao.observacao = this.campoObservacao.value;
        this.conciliacao.quantidadeOitivas = this.campoQuantidadeOitivas.value;
        this.conciliacao.nomeConciliador = this.campoNomeConciliador.value;
        this.conciliacao.agendamento = this.formatarDataHora();
        this.conciliacao.statusAgendamento = 'CONFIRMADO';
      }
      else{
        this.conciliacao.duracaoEstimada = 1440;
        this.conciliacao.quantidadeOitivas = 1;
        this.conciliacao.nomeConciliador = '-';

        const valueFieldDate: string = this.campoData.value;
        const arrayDate = valueFieldDate.split('/');
        const date = arrayDate[2] + '-' + arrayDate[1] + '-' + arrayDate[0] + ' ' + '00:00';

        this.conciliacao.agendamento = date;
        this.conciliacao.statusAgendamento = 'CONFIRMADO';
      }

      if (!this.isEdicao) {
        this.agendamentoService.cadastrarConciliacao(this.conciliacao)
          .then((conciliacaoAdicionada) => {
            this.router.navigate(['/agendamentos']);
            this.snackBar.open('Conciliação cadastrada com sucesso', '', { duration: 4500});
          })
          .catch(erro => {
            this.errorHandlerService.handle(erro);
          }
        );
      }
      else{
        if (this.isEdicaoAgendamentoReservado) {
          this.agendamentoService.excluirConciliacao(this.codigoAgendamentoReservado).then(() => {

            this.agendamentoService.cadastrarConciliacao(this.conciliacao)
              .then((conciliacaoAdicionada) => {

                this.router.navigate(['/agendamentos']);
                this.snackBar.open('Conciliação cadastrada com sucesso', '', { duration: 4500});
              })
              .catch(erro => {
                this.errorHandlerService.handle(erro);
              }
            );
          });
        }
        else{
          this.atualizarConciliacao();
        }
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
      }
    );
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
      }
    );
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

    if (this.activatedRoute.snapshot.data.tipo === 'audiencia') {
      this.agendamentoService.buscarAudienciaPorCodigo(codigo)
        .then(audiencia => {

          this.campoTipoSessao.setValue('Audiência');

          if (audiencia.processo.numeroProcesso !== '-') {

            this.audiencia = audiencia;
            this.processo = audiencia.processo;

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
          }
          else{ // edição de um agendamento reservado
            this.isEdicaoAgendamentoReservado = true;

            this.agendamentoService.buscarAudienciaPorCodigo(codigo)
            .then(audienciaReservada => {

              this.codigoAgendamentoReservado = audienciaReservada.codigo;

              // const agendamentoReservado: Audiencia = audienciaReservada;

              this.preencherDataHoraAudiencia(audienciaReservada);
            })
            .catch(erro => this.errorHandlerService.handle(erro));
          }
        })
        .catch(erro => this.errorHandlerService.handle(erro));
    }
    else if (this.activatedRoute.snapshot.data.tipo === 'conciliacao') {
      this.agendamentoService.buscarConciliacaoPorCodigo(codigo)
        .then(conciliacao => {

          this.campoTipoSessao.setValue('Conciliação');

          if (conciliacao.processo.numeroProcesso !== '-') {

            this.processo = conciliacao.processo;
            this.conciliacao = conciliacao;

            this.preencherDataHoraConciliacao(this.conciliacao);
            this.campoNumeroProcesso.setValue(this.conciliacao.processo.numeroProcesso);
            this.campoNomeParte.setValue(this.conciliacao.processo.nomeDaParte);
            this.campoTempoDuracao.setValue(this.conciliacao.duracaoEstimada);
            this.campoObservacao.setValue(this.conciliacao.observacao);
            this.campoQuantidadeOitivas.setValue(this.conciliacao.quantidadeOitivas);
            this.campoNomeConciliador.setValue(this.conciliacao.nomeConciliador);
          }
          else{ // edição de um agendamento reservado

            this.isEdicaoAgendamentoReservado = true;

            this.agendamentoService.buscarConciliacaoPorCodigo(codigo)
            .then(conciliacaoReservada => {

              this.codigoAgendamentoReservado = conciliacaoReservada.codigo;

              // const agendamentoReservado: Audiencia = audienciaReservada;

              this.preencherDataHoraConciliacao(conciliacaoReservada);
            })
            .catch(erro => this.errorHandlerService.handle(erro));
          }
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
    else if (tipoAudiencia === 'Custódia'){
      return 'CUSTODIA';
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
  if (ano < 2000) { // Number(new Date().getFullYear() - 1);
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

export function ValidateHour(control: AbstractControl){

  const valorCampoHora = control.value;

  const time = valorCampoHora.split(':');

  const hour: number = time[0];
  const minutes: number = time[1];

  if (hour > 23) {
    return { validHour: true};
  }
  if (minutes > 59){
    return { validHour: true};
  }

  return null;
}

