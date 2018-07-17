import { UsuarioService } from './../../usuario/usuario.service';
import { SelectionModel } from '@angular/cdk/collections';
import { Conciliacao, Audiencia, Processo, ParteInteressada, Pendencia, Usuario } from './../../core/model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorHandlerService } from './../../core/error-handler.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AgendamentoService } from './../agendamento.service';
import { MatSort } from '@angular/material/sort';
import { FormControl, Validators, FormGroup, AbstractControl } from '@angular/forms';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatDialog } from '@angular/material';
import { StorageDataService } from '../../core/storage-data.service';
import { LoadingComponent } from '../../core/loading.component';

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
  campoTipoAudiencia: FormControl;

  campoNomeParteInteressada: FormControl;
  campoEmailParteInteressada: FormControl;
  campoFuncaoParteInteressada: FormControl;

  campoPendencia: FormControl;
  campoResponsavel: FormControl;

  public mascaraData = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/];
  public mascaraHora = [/\d/, /\d/, ':', /\d/, /\d/];

  tempoDuracaoAudienciaEscolhida = 0;
  tipoAudiencia = 'Ação civil';
  tempoDuracao = 1;
  isEdicao = false;
  isEdicaoAgendamentoReservado = false;
  codigoAgendamentoReservado;
  isCamposPartesInteressadasInvalidos = true;

  dataSourcePartesInteressadas: MatTableDataSource<ParteInteressada>;
  colunasPartesInteressadas = ['nome', 'email', 'funcao', 'acoes']; // 'acoes'

  conciliacao = new Conciliacao();
  audiencia = new Audiencia();
  processo = new Processo();
  // pendencias: Pendencia[];

  tiposAudiencias = [
    {nome: 'Ação civil', duracao: 20}, {nome: 'Custódia', duracao: 20}, {nome: 'Improbidade', duracao: 20},
    {nome: 'Instrução do creta', duracao: 7}, {nome: 'Leilão', duracao: 60},
    {nome: 'Outros', duracao: 20}, {nome: 'Penal', duracao: 20}, {nome: 'PJE', duracao: 20},
    {nome: 'Tebas improbidade', duracao: 20}, {nome: 'Videoconferência', duracao: 20}
  ];

  /*-------------- PENDENCIAS ---------------*/
  isCamposPendenciasInvalidos = true;
  colunasPendencias = ['resolvida', 'descricao', 'responsavel', 'acoes'];
  usuarios: Usuario[];
  nomeResponsavel = '';
  dataSourcePendencias: MatTableDataSource<Pendencia>;
  selection = new SelectionModel<Pendencia>(true, []);
  /*-------------- PENDENCIAS ---------------*/

  constructor(private agendamentoService: AgendamentoService, private activatedRoute: ActivatedRoute,
      private snackBar: MatSnackBar, private router: Router, private dialog: MatDialog,
      private errorHandlerService: ErrorHandlerService, private storageDataService: StorageDataService,
      private usuarioService: UsuarioService) {

    const codigoAgendamento = this.activatedRoute.snapshot.params['codigo'];
    const agendamentoReservado = this.activatedRoute.snapshot.data.tipo === 'reservado';

    if (codigoAgendamento) {
      this.isEdicao = true;
      this.carregarAgendamento(codigoAgendamento);

      // if (agendamentoReservado) {
      //   this.isEdicaoReserva = true;
      // }
    }

    this.dataSourcePartesInteressadas = new MatTableDataSource(); // dadosPartesInteressadas
    this.dataSourcePendencias = new MatTableDataSource<Pendencia>();
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
    this.campoTipoAudiencia = new FormControl('Ação civil', [Validators.required]);

    this.campoNomeParteInteressada = new FormControl(''); // '', [Validators.required]
    this.campoEmailParteInteressada = new FormControl('', [Validators.email]);
    this.campoFuncaoParteInteressada = new FormControl(''); // '', [Validators.required]

    /*------ PENDENCIAS ----------*/
    this.usuarioService.listarTodos()
      .then(response => {
        this.usuarios = response.usuarios;

        // Removendo usuário publico
        const usuarioPublico: Usuario = this.usuarios.filter(usuarioFiltrado =>
            usuarioFiltrado.nome === 'Public')[0];

        this.usuarios.splice(this.usuarios.indexOf(usuarioPublico), 1);
      });

    this.campoPendencia = new FormControl();
    this.campoResponsavel = new FormControl('Escolha um responsável');
    /*------ PENDENCIAS ----------*/

    this.setTempoDuracao(20, 1);
    this.recalcularTempoDuracao(1);
  }

  /*------- PARTES INTERESSADAS-------------*/
  adicionarParte() {

    const parteInteressada = new ParteInteressada();
    parteInteressada.nome = this.campoNomeParteInteressada.value;
    parteInteressada.email = this.campoEmailParteInteressada.value;
    parteInteressada.funcao = this.campoFuncaoParteInteressada.value;

    const partesInteressadas: ParteInteressada[] = this.dataSourcePartesInteressadas.data;

    partesInteressadas.push(parteInteressada);

    this.dataSourcePartesInteressadas.data = partesInteressadas;

    this.campoNomeParteInteressada.setValue('');
    this.campoNomeParteInteressada.markAsUntouched();

    this.campoEmailParteInteressada.setValue('');
    this.campoEmailParteInteressada.markAsUntouched(); // remover depois

    this.campoFuncaoParteInteressada.setValue('');
    this.campoFuncaoParteInteressada.markAsUntouched();

    this.isCamposPartesInteressadasInvalidos = true;
  }

  removerParte(parteInteressada: ParteInteressada) {

    const partesInteressadas: ParteInteressada[] = this.dataSourcePartesInteressadas.data;

      // Removendo parte interessada
      partesInteressadas.splice(partesInteressadas.indexOf(parteInteressada), 1);

      this.dataSourcePartesInteressadas.data = partesInteressadas;
  }

  validarBotaoParteInteressada() {
    if (this.campoNomeParteInteressada.value === '' || this.campoEmailParteInteressada.value === '' ||
        this.campoFuncaoParteInteressada.value === ''){
      this.isCamposPartesInteressadasInvalidos = true;
    }
    else if (this.campoNomeParteInteressada.value !== '' && this.campoEmailParteInteressada.value !== '' &&
        this.campoFuncaoParteInteressada.value !== '') {
      this.isCamposPartesInteressadasInvalidos = false;
    }

  }
  /*------- PARTES INTERESSADAS-------------*/

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

  cancelarCadastro(){
    this.router.navigate(['/agendamentos']);
  }

  cadastrar()
  {
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

      /*
       "setando" os dados da audiência
      */
      this.audiencia.processo = this.processo;

      if (this.audiencia.agendamento !== this.formatarDataHora()) {
        this.audiencia.statusAgendamento = 'CONFIRMADO';
      }

      if (!this.isCadastroReserva()) {
        this.audiencia.duracaoEstimada = this.campoTempoDuracao.value;
        this.audiencia.observacao = this.campoObservacao.value;
        this.audiencia.quantidadeOitivas = this.campoQuantidadeOitivas.value;
        this.audiencia.partesInteressadas = this.dataSourcePartesInteressadas.data;
        this.audiencia.pendencias = this.dataSourcePendencias.data;

        if (this.campoHora.value === ''){
          return this.snackBar.open('Você esqueceu de informar a hora', '',
              {panelClass: ['snack-bar-error'], duration: 4500});
        }
        else{
          this.audiencia.agendamento = this.formatarDataHora();
        }
      }
      else {
        this.audiencia.duracaoEstimada = 1440;
        this.audiencia.quantidadeOitivas = 1;

        const valueFieldDate: string = this.campoData.value;
        const arrayDate = valueFieldDate.split('/');
        const date = arrayDate[2] + '-' + arrayDate[1] + '-' + arrayDate[0] + ' ' + '00:00';

        this.audiencia.agendamento = date;
      }

      // Chamando a URL de cadastro na API para salvar a audiência no banco
      if (!this.isEdicao)
      {
        this.dialog.open(LoadingComponent, { disableClose: true });

        this.agendamentoService.cadastrarAudiencia(this.audiencia)
        .then((audienciaAdicionada) => {
          this.router.navigate(['/agendamentos']);

          this.dialog.closeAll();

          this.snackBar.open('Audiência cadastrada com sucesso', '', { duration: 4500});
        })
        .catch(erro => {
          this.errorHandlerService.handle(erro);
          this.dialog.closeAll();
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
      /*
        "setando" os dados da conciliacao
      */
      this.conciliacao.processo = this.processo;

      if (this.conciliacao.agendamento !== this.formatarDataHora()) {
        this.conciliacao.statusAgendamento = 'CONFIRMADO';
      }

      if (!this.isCadastroReserva()) {
        this.conciliacao.duracaoEstimada = this.campoTempoDuracao.value;
        this.conciliacao.observacao = this.campoObservacao.value;
        this.conciliacao.quantidadeOitivas = this.campoQuantidadeOitivas.value;
        this.conciliacao.nomeConciliador = this.campoNomeConciliador.value;
        this.conciliacao.partesInteressadas = this.dataSourcePartesInteressadas.data;
        this.conciliacao.pendencias = this.dataSourcePendencias.data;

        if (this.campoHora.value === ''){
          return this.snackBar.open('Você esqueceu de informar a hora', '',
              {panelClass: ['snack-bar-error'], duration: 4500});
        }
        else{
          this.conciliacao.agendamento = this.formatarDataHora();
        }
      }
      else{
        this.conciliacao.duracaoEstimada = 1440;
        this.conciliacao.quantidadeOitivas = 1;
        this.conciliacao.nomeConciliador = '-';

        const valueFieldDate: string = this.campoData.value;
        const arrayDate = valueFieldDate.split('/');
        const date = arrayDate[2] + '-' + arrayDate[1] + '-' + arrayDate[0] + ' ' + '00:00';

        this.conciliacao.agendamento = date;
      }

      // Chamando a URL de cadastro na API para salvar a conciliação no banco
      if (!this.isEdicao)
      {
        this.dialog.open(LoadingComponent, { disableClose: true });

        this.agendamentoService.cadastrarConciliacao(this.conciliacao)
          .then((conciliacaoAdicionada) => {
            this.router.navigate(['/agendamentos']);

            this.dialog.closeAll();

            this.snackBar.open('Conciliação cadastrada com sucesso', '', { duration: 4500});
          })
          .catch(erro => {
            this.errorHandlerService.handle(erro);
            this.dialog.closeAll();
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

  atualizarAudiencia()
  {
    this.audiencia.duracaoEstimada = this.campoTempoDuracao.value;
    this.audiencia.observacao = this.campoObservacao.value;
    this.audiencia.quantidadeOitivas = this.campoQuantidadeOitivas.value;
    this.audiencia.tipoAudiencia = this.converterTipoAudienciaLabelParaEnum(this.tipoAudiencia);
    this.audiencia.agendamento = this.formatarDataHora();
    this.audiencia.partesInteressadas = this.dataSourcePartesInteressadas.data;

    // console.log(this.dataSourcePendencias.data);

    this.audiencia.pendencias = this.dataSourcePendencias.data;

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

  atualizarConciliacao()
  {
    this.conciliacao.duracaoEstimada = this.campoTempoDuracao.value;
    this.conciliacao.observacao = this.campoObservacao.value;
    this.conciliacao.quantidadeOitivas = this.campoQuantidadeOitivas.value;
    this.conciliacao.nomeConciliador = this.campoNomeConciliador.value;
    this.conciliacao.agendamento = this.formatarDataHora();
    this.conciliacao.partesInteressadas = this.dataSourcePartesInteressadas.data;
    this.conciliacao.pendencias = this.dataSourcePendencias.data;

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

  private atualizarProcesso()
  {
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

  carregarAgendamento(codigo: number)
  {
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

            this.dataSourcePartesInteressadas.data = this.audiencia.partesInteressadas;

            this.dataSourcePendencias.data = this.audiencia.pendencias;

            this.audiencia.pendencias.forEach(pendencia => {
              if (pendencia.resolvida){
                this.selection.select(pendencia);
              }
            });

            // console.log('Carregando pendencias...');
            // console.log(this.audiencia.pendencias);
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

            this.dataSourcePartesInteressadas.data = this.conciliacao.partesInteressadas;

            this.dataSourcePendencias.data = this.conciliacao.pendencias;

            this.conciliacao.pendencias.forEach(pendencia => {
              if (pendencia.resolvida){
                this.selection.select(pendencia);
              }
            });

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

  /*------------- PENDENCIAS -----------*/

  resolverPendencia(pendencia: Pendencia) {
    pendencia.resolvida = !pendencia.resolvida;
  }

  private salvarPendencias()
  {
    this.selection.selected.forEach(pendencia => {
      pendencia.resolvida = true;
    });

    // this.pendencias = this.dataSourcePendencias.data;

    // this.storageDataServi.setPendencias(this.dataSourcePendencias.data);
  }

  validarBotaoPendencia()
  {
    if (this.campoPendencia.value === '' || this.nomeResponsavel === ''){
      this.isCamposPendenciasInvalidos = true;
    }
    else if (this.campoPendencia.value !== '' && this.nomeResponsavel !== '') {
      this.isCamposPendenciasInvalidos = false;
    }
  }

  adicionarPendencia()
  {
    const pendencia = new Pendencia();
    pendencia.descricao = this.campoPendencia.value;
    pendencia.responsavel = this.nomeResponsavel;

    const pendencias: Pendencia[] = this.dataSourcePendencias.data;

    pendencias.push(pendencia);

    this.dataSourcePendencias.data = pendencias;

    this.campoPendencia.setValue('');
    this.campoResponsavel.setValue('Responsável');

    this.isCamposPendenciasInvalidos = true;

    this.salvarPendencias();
  }

  removerPendencia(pendencia: Pendencia)
  {
    const pendencias: Pendencia[] = this.dataSourcePendencias.data;

    // Removendo parte interessada
    pendencias.splice(pendencias.indexOf(pendencia), 1);

    this.dataSourcePendencias.data = pendencias;
  }
  /*------------- PENDENCIAS -----------*/

  //            para cadastro            [0] [1] [2]
  private formatarDataHora(): string
  { // aaaa-MM-dd HH:mm
    const valorCampoData: string = this.campoData.value;

    const data = valorCampoData.split('/');

    return data[2] + '-' + data[1] + '-' + data[0] + ' ' + this.campoHora.value;
  }

  private preencherDataHoraAudiencia(audiencia: Audiencia)
  {
    const dataHora = audiencia.agendamento.split(' ');

    const data = dataHora[0];
    const hora = dataHora[1];

    const arrayData = data.split('-');

    // const dataFormatada = arrayData[2] + '/' + arrayData[1] + '/' + arrayData[0];

    this.campoData.setValue(arrayData[2] + '/' + arrayData[1] + '/' + arrayData[0]);
    this.campoHora.setValue(hora);
  }

  private preencherDataHoraConciliacao(conciliacao: Conciliacao)
  {
    const dataHora = conciliacao.agendamento.split(' ');

    const data = dataHora[0];
    const hora = dataHora[1];

    const arrayData = data.split('-');

    // const dataFormatada = arrayData[2] + '/' + arrayData[1] + '/' + arrayData[0];

    this.campoData.setValue(arrayData[2] + '/' + arrayData[1] + '/' + arrayData[0]);
    this.campoHora.setValue(hora);
  }

  isAudiencia(): boolean
  {
    if (this.campoTipoSessao.value === 'Audiência'){ // this.tipoSessaoEscolhido === 'Audiência' ||
      return true;
    }
    return false;
  }

  private converterTipoAudienciaLabelParaEnum(tipoAudiencia: any): string
  {
    if (tipoAudiencia === 'Ação civil'){
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

  private converterTipoAudienciaEnumParaLabel(tipoAudiencia: string): string
  {
    if (tipoAudiencia === 'ACAO_CIVIL'){
      return 'Ação civil';
    }
    else if (tipoAudiencia === 'CUSTODIA') {
      return 'Custódia';
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

export function ValidateDate(control: AbstractControl)
{
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

export function ValidateHour(control: AbstractControl)
{
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

