// import { element } from 'protractor';
import { Router } from '@angular/router';
import { AuthService } from './../../seguranca/auth.service';
import { Audiencia, Conciliacao } from './../../core/model';
import { AgendamentoService } from './../agendamento.service';
import { StorageDataService } from './../../core/storage-data.service';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { Component, OnInit, ViewChild, Input, AfterViewInit, Inject, Injectable } from '@angular/core';
import { MatTableDataSource, MatBottomSheetRef, MatBottomSheet,
    MAT_BOTTOM_SHEET_DATA, MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { Observable } from 'rxjs';
import { ErrorHandlerService } from '../../core/error-handler.service';
import { AudienciaFilter, ConciliacaoFilter } from './../../core/model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { FormControl, Validators, AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-agendamento-tabela',
  templateUrl: './agendamento-tabela.component.html',
  styleUrls: ['./agendamento-tabela.component.css']
})
export class AgendamentoTabelaComponent implements OnInit, AfterViewInit {

  @Input() isAudiencia: boolean;
  @ViewChild('sort') sort: MatSort;

  dataSourceAudiencias: MatTableDataSource<Audiencia> = new MatTableDataSource();
  dataSourceConciliacoes: MatTableDataSource<Conciliacao> = new MatTableDataSource();

  displayedColumns = ['numeroProcesso', 'agendamento', 'statusAgendamento', 'acoes']; // 'status',

  constructor(private agendamentoService: AgendamentoService,
      public storageDataService: StorageDataService,
      private authService: AuthService, private router: Router,
      private bottomSheet: MatBottomSheet, private dialog: MatDialog) {
  }

  ngOnInit() {}

  ngAfterViewInit() {
    if (this.isAudiencia) {
      this.listarAudiencias();
    }
    else{
      this.listarConciliacoes();
    }
  }

  openDialog() {

    let tabelaRef: MatTableDataSource<any> = this.dataSourceConciliacoes;

    if (this.isAudiencia) {
      tabelaRef = this.dataSourceAudiencias;
    }

    const dialogRef = this.dialog.open(AgendamentoPesquisaAvancadaComponent, {
      height: '90%', width: '99%',
      data: {
        isAudiencia: this.isAudiencia,
        tabelaRef: tabelaRef,
      }
    });
  }

  openBottomSheet(sessao: any): void {

    let isAudiencia = false;
    let tabelaRef: MatTableDataSource<any> = this.dataSourceConciliacoes;

    if (sessao.tipoAudiencia) {
      isAudiencia = true;
      tabelaRef = this.dataSourceAudiencias;
    }

    this.bottomSheet.open(AtualizacaoStatusAgendamentoComponent, {
      data: {
        isAudiencia: isAudiencia,
        sessao: sessao,
        tabelaRef: tabelaRef
      }
    });
  }

  listarAudiencias() {

    if (this.storageDataService.isFiltragemConcluida()){
      this.storageDataService.setFiltragemConcluida(false);
    }

    this.agendamentoService.listarAudiencias()
      .then(resultado => {

        this.dataSourceAudiencias.data = resultado.audiencias;

        this.dataSourceAudiencias.sort = this.sort;
      });
  }

  listarConciliacoes() {

    if (this.storageDataService.isFiltragemConcluida()){
      this.storageDataService.setFiltragemConcluida(false);
    }

    this.agendamentoService.listarConciliacoes()
      .then(resultado => {

        this.dataSourceConciliacoes.data = resultado.conciliacoes;

        this.dataSourceConciliacoes.sort = this.sort;
      });
  }

  editar(codigo: number){
    if (this.isAudiencia){
      this.router.navigate(['/agendamentos/audiencia', codigo]);
    }
    else{
      this.router.navigate(['/agendamentos/conciliacao', codigo]);
    }
  }

}

@Component({
  selector: 'app-atualizacao-status-agendamento',
  template: `
    <mat-nav-list>
      <mat-list-item *ngFor="let status of opcoesStatus"
          (click)="atualizarStatus(status.nome)">
        <mat-icon mat-list-icon>{{status.icone}}</mat-icon>
        <a matLine>{{status.nome}}</a>
      </mat-list-item>
    </mat-nav-list>
  `,
  styleUrls: ['./agendamento-tabela.component.css']
})
export class AtualizacaoStatusAgendamentoComponent {

  constructor(@Inject(MAT_BOTTOM_SHEET_DATA) public data: any,
      private bottomSheetRef: MatBottomSheetRef<AtualizacaoStatusAgendamentoComponent>,
      private agendamentoService: AgendamentoService, private errorHandlerService: ErrorHandlerService) {
  }

  opcoesStatus = [
    {nome: 'ADIAR', icone: 'access_time'},
    {nome: 'CANCELAR', icone: 'clear'}
  ];

  atualizarStatus(status: string) {

    if (status === 'ADIAR') {
      status = 'ADIADO';
    }
    else {
      status = 'CANCELADO';
    }

    if (this.data.isAudiencia) {

      const audiencia: Audiencia = this.data.sessao;

      audiencia.statusAgendamento = status;
      audiencia.tipoAudiencia = this.converterTipoAudienciaLabelParaEnum(audiencia.tipoAudiencia);

      this.agendamentoService.atualizarAudiencia(audiencia)
        .then(() => {
          this.agendamentoService.listarAudiencias()
            .then(resultado => {

              this.data.data = resultado.conciliacoes;
            }
          );
        }
      )
      .catch(erro => {
        this.errorHandlerService.handle(erro);
      });
    }
    else {
      const conciliacao: Conciliacao = this.data.sessao;

      conciliacao.statusAgendamento = status;

      this.agendamentoService.atualizarConciliacao(conciliacao)
        .then(() => {
          this.agendamentoService.listarConciliacoes()
            .then(resultado => {

              this.data.data = resultado.conciliacoes;
            }
          );
        }
      )
      .catch(erro => {
        this.errorHandlerService.handle(erro);
      });
    }

    this.bottomSheetRef.dismiss();
  }

  private converterTipoAudienciaLabelParaEnum(tipoAudiencia: any): string{
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

}

@Component({
  selector: 'app-agendamento-pesquisa-avancada',
  templateUrl: './agendamento-pesquisa-avancada-component.html',
  styleUrls: ['./agendamento-tabela.component.css']
})
export class AgendamentoPesquisaAvancadaComponent implements OnInit {

  enableButton = false;
  mascaraData = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/, ' ', /\d/, /\d/, ':', /\d/, /\d/];

  isNumeroProcessoChecked = false;
  isNomeParteChecked = false;
  isQuantidadeOitivasChecked = false;
  isStatusChecked = false;
  isTipoAudienciaChecked = false;
  isNomeConciliadorChecked = false;
  isDataSessaoChecked = false;
  isTempoDuracaoChecked = false;
  isObservacaoChecked = false;

  campoNumeroProcesso: FormControl = new FormControl({value: '', disabled: true});
  campoNomeParte: FormControl = new FormControl({value: '', disabled: true});
  campoOitivasA: FormControl = new FormControl({value: 1, disabled: true});
  campoOitivasB: FormControl = new FormControl({value: 1, disabled: true});
  campoStatus: FormControl = new FormControl({value: '', disabled: true});
  campoTipoAudiencia: FormControl = new FormControl({value: '', disabled: true});
  campoConciliador: FormControl = new FormControl({value: '', disabled: true});
  campoDataSessaoA: FormControl = new FormControl({value: '', disabled: true}, [Validators.minLength(16), ValidateDate]);
  campoDataSessaoB: FormControl = new FormControl({value: '', disabled: true}, [Validators.minLength(16), ValidateDate]);
  campoDuracaoA: FormControl = new FormControl({value: 1, disabled: true});
  campoDuracaoB: FormControl = new FormControl({value: 1, disabled: true});
  campoObservacao: FormControl = new FormControl({value: '', disabled: true});

  tiposAudiencias = [
    {label: 'Ação civil', enum: 'ACAO_CIVIL'}, {label: 'Custódia', enum: 'CUSTODIA'},
    {label: 'Improbidade', enum: 'IMPROBIDADE'}, {label: 'Instrução do creta', enum: 'INSTRUCAO_CRETA'},
    {label: 'Leilão', enum: 'LEILAO'}, {label: 'Outros', enum: 'OUTROS'}, {label: 'Penal', enum: 'PENAL'},
    {label: 'PJE', enum: 'PJE'}, {label: 'Tebas improbidade', enum: 'TEBAS_IMPROBIDADE'},
    {label: 'Videoconferência', enum: 'VIDEOCONFERENCIA'}
  ];

  tiposStatus = ['CONFIRMADO', 'ADIADO', 'CANCELADO'];

  constructor(private snackBar: MatSnackBar, private agendamentoService: AgendamentoService,
    private errorHandlerService: ErrorHandlerService, @Inject(MAT_DIALOG_DATA) public data: any,
    public dialog: MatDialog, private storageDataService: StorageDataService) {}

  ngOnInit(){}

  aplicarFiltro() {
    if (this.data.isAudiencia){
      this.filtrarAudiencia();
    }
    else {
      this.filtrarConciliacao();
    }
  }

  private filtrarConciliacao() {

    const inputArray: FormControl[] = [this.campoNumeroProcesso, this.campoNomeParte,
      this.campoOitivasA, this.campoOitivasB, this.campoStatus, this.campoObservacao,
      this.campoConciliador, this.campoDataSessaoA, this.campoDataSessaoB,
      this.campoDuracaoA, this.campoDuracaoB
    ];

    for (const input of inputArray){
      if (input.invalid && input.enabled){
        return this.snackBar.open('Preencha corretamente os campos escolhidos ou desmarque aqueles que deseja ignorar',
          '', {panelClass: ['snack-bar-error'], duration: 4000});
      }
    }

    if (this.isCamposNumericosValidos()) {

      const conciliacaoFilter: ConciliacaoFilter = new ConciliacaoFilter();

      if (this.campoNumeroProcesso.enabled){
        conciliacaoFilter.numeroProcesso = this.campoNumeroProcesso.value;
      }
      if (this.campoNomeParte.enabled){
        conciliacaoFilter.nomeDaParteProcesso = this.campoNomeParte.value;
      }
      if (this.campoOitivasA.enabled){
        conciliacaoFilter.quantidadeOitivasDe = this.campoOitivasA.value;
        conciliacaoFilter.quantidadeOitivasAte = this.campoOitivasB.value;
      }
      if (this.campoStatus.enabled) {
        conciliacaoFilter.statusAgendamento = this.campoStatus.value;
      }
      if (this.campoConciliador.enabled) {
        conciliacaoFilter.nomeConciliador = this.campoConciliador.value;
      }
      if (this.campoDataSessaoA.enabled) {

        const dataHoraA = this.campoDataSessaoA.value.split(' ');
        const dataArrayA = dataHoraA[0].split('/');
        const tempoA = dataHoraA[1];

        const dataHoraB = this.campoDataSessaoB.value.split(' ');
        const dataArrayB = dataHoraB[0].split('/');
        const tempoB = dataHoraB[1];

        conciliacaoFilter.dataAgendamentoDe = dataArrayA[2] + '-' + dataArrayA[1] + '-' + dataArrayA[0] + ' ' + tempoA;
        conciliacaoFilter.dataAgendamentoAte = dataArrayB[2] + '-' + dataArrayB[1] + '-' + dataArrayB[0] + ' ' + tempoB;
      }
      if (this.campoDuracaoA.enabled) {
        conciliacaoFilter.duracaoEstimadaDe = this.campoDuracaoA.value;
        conciliacaoFilter.duracaoEstimadaAte = this.campoDuracaoB.value;
      }
      if (this.campoObservacao.enabled) {
        conciliacaoFilter.observacao = this.campoObservacao.value;
      }

      this.agendamentoService.filtrarConciliacao(conciliacaoFilter)
        .then(resultado => {
          this.data.tabelaRef.data = resultado.conciliacoes;
          this.storageDataService.setFiltragemConcluida(true);
          this.dialog.closeAll();
        })
        .catch(erro => this.errorHandlerService.handle(erro));
    }

  }

  private filtrarAudiencia() {

    const inputArray: FormControl[] = [this.campoNumeroProcesso, this.campoNomeParte,
      this.campoOitivasA, this.campoOitivasB, this.campoStatus, this.campoObservacao,
      this.campoTipoAudiencia, this.campoDataSessaoA,
      this.campoDataSessaoB, this.campoDuracaoA, this.campoDuracaoB
    ];

    for (const input of inputArray){
      if (input.invalid && input.enabled){
        return this.snackBar.open('Preencha corretamente os campos escolhidos ou desmarque aqueles que deseja ignorar',
          '', {panelClass: ['snack-bar-error'], duration: 4000});
      }
    }

    if (this.isCamposNumericosValidos()) {

      const audienciaFilter: AudienciaFilter = new AudienciaFilter();

      if (this.campoNumeroProcesso.enabled){
        audienciaFilter.numeroProcesso = this.campoNumeroProcesso.value;
      }
      if (this.campoNomeParte.enabled){
        audienciaFilter.nomeDaParteProcesso = this.campoNomeParte.value;
      }
      if (this.campoOitivasA.enabled){
        audienciaFilter.quantidadeOitivasDe = this.campoOitivasA.value;
        audienciaFilter.quantidadeOitivasAte = this.campoOitivasB.value;
      }
      if (this.campoStatus.enabled) {
        audienciaFilter.statusAgendamento = this.campoStatus.value;
      }
      if (this.campoTipoAudiencia.enabled) {
        audienciaFilter.tipoAudiencia = this.campoTipoAudiencia.value;
      }
      if (this.campoDataSessaoA.enabled) {

        const dataHoraA = this.campoDataSessaoA.value.split(' ');
        const dataArrayA = dataHoraA[0].split('/');
        const tempoA = dataHoraA[1];

        const dataHoraB = this.campoDataSessaoB.value.split(' ');
        const dataArrayB = dataHoraB[0].split('/');
        const tempoB = dataHoraB[1];

        audienciaFilter.dataAgendamentoDe = dataArrayA[2] + '-' + dataArrayA[1] + '-' + dataArrayA[0] + ' ' + tempoA;
        audienciaFilter.dataAgendamentoAte = dataArrayB[2] + '-' + dataArrayB[1] + '-' + dataArrayB[0] + ' ' + tempoB;
      }
      if (this.campoDuracaoA.enabled) {
        audienciaFilter.duracaoEstimadaDe = this.campoDuracaoA.value;
        audienciaFilter.duracaoEstimadaAte = this.campoDuracaoB.value;
      }
      if (this.campoObservacao.enabled) {
        audienciaFilter.observacao = this.campoObservacao.value;
      }

      this.agendamentoService.filtrarAudiencia(audienciaFilter)
        .then(resultado => {
          this.data.tabelaRef.data = resultado.audiencias;
          this.storageDataService.setFiltragemConcluida(true);
          this.dialog.closeAll();
        })
        .catch(erro => this.errorHandlerService.handle(erro));
    }
  }

  isCamposNumericosValidos(): boolean {

    if (this.campoOitivasA.enabled) {
      if (this.campoOitivasA.value > this.campoOitivasB.value){
        this.snackBar.open('A quantidade de oitivas inicial não pode ser maior que a quantidade de oitivas final',
            '', {panelClass: ['snack-bar-error'], duration: 4000});
        return false;
      }
    }
    if (this.campoDataSessaoA.enabled) {

      const dataHoraA = this.campoDataSessaoA.value.split(' ');
      const dataArrayA = dataHoraA[0].split('/');
      const tempoA = dataHoraA[1];

      const dataHoraB = this.campoDataSessaoB.value.split(' ');
      const dataArrayB = dataHoraB[0].split('/');
      const tempoB = dataHoraB[1];

      const dataA = new Date(dataArrayA[1] + '/' + dataArrayA[0] + '/' + dataArrayA[2] + ' ' + tempoA);
      const dataB = new Date(dataArrayB[1] + '/' + dataArrayB[0] + '/' + dataArrayB[2] + ' ' + tempoB);

      if (dataA > dataB) {
        this.snackBar.open('A data inicial não pode ser maior que a data final',
            '', {panelClass: ['snack-bar-error'], duration: 4000});
        return false;
      }
    }
    if (this.campoDuracaoA.enabled) {
      if (this.campoDuracaoA.value > this.campoDuracaoB.value) {
        this.snackBar.open('O tempo de duração inicial não pode ser maior que o tempo de duração final',
            '', {panelClass: ['snack-bar-error'], duration: 4000});
        return false;
      }
    }

    return true;
  }

  setEnable(formControl: AbstractControl, isDisabled: boolean,
      campoDuplo?: boolean, tipoCampoDuplo?: string) {

    if (campoDuplo && !isDisabled) {
      if (tipoCampoDuplo === 'oitivas') {
        this.campoOitivasA.enable();
        this.campoOitivasB.enable();
      }
      else if (tipoCampoDuplo === 'data'){
        this.campoDataSessaoA.enable();
        this.campoDataSessaoB.enable();
      }
      else if (tipoCampoDuplo === 'duracao') {
        this.campoDuracaoA.enable();
        this.campoDuracaoB.enable();
      }
    }
    else if (campoDuplo && isDisabled) {
      if (tipoCampoDuplo === 'oitivas') {
        this.campoOitivasA.disable();
        this.campoOitivasB.disable();
      }
      else if (tipoCampoDuplo === 'data'){
        this.campoDataSessaoA.disable();
        this.campoDataSessaoB.disable();
      }
      else if (tipoCampoDuplo === 'duracao') {
        this.campoDuracaoA.disable();
        this.campoDuracaoB.disable();
      }
    }
    else if (isDisabled) {
      formControl.disable();
    }
    else {
      formControl.enable();
    }

    const inputArray: FormControl[] = [this.campoNumeroProcesso, this.campoNomeParte,
      this.campoOitivasA, this.campoOitivasB, this.campoStatus, this.campoObservacao,
      this.campoTipoAudiencia, this.campoConciliador, this.campoDataSessaoA,
      this.campoDataSessaoB, this.campoDuracaoA, this.campoDuracaoB
    ];

    let contDisable = 0;

    for (const input of inputArray){

      if (input.disabled){
        contDisable += 1;
      }
    }

    if (contDisable > 11) {
      this.enableButton = false;
    }
    else{
      this.enableButton = true;
    }
  }
}

export function ValidateDate(control: AbstractControl){
  // TENTAR CADASTRAR UM AGENDAMENTO NO MES 12
  const valorCampoDataHora = control.value;

  if (valorCampoDataHora !== ''){

    const dataHora = valorCampoDataHora.split(' ');

    const data = dataHora[0].split('/');

    const tempo = dataHora[1].split(':');

    const dia: number = data[0];
    const mes: number = data[1];
    const ano: number = data[2];
    const hora: number = tempo[0];
    const minutos: number = tempo[1];

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

    if (hora > 23) {
      return { validDate: true};
    }
    if (minutos > 59){
      return { validDate: true};
    }
  }

  return null;
}

