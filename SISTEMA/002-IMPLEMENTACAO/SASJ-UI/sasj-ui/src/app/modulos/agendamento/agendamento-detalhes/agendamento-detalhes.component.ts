import { Audiencia, Conciliacao } from './../../core/model';
import { MatSort } from '@angular/material/sort';
import { Component, OnInit, ViewChild, Input, Inject } from '@angular/core';
import { MatDialog, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-agendamento-detalhes',
  template: `
    <button mat-icon-button (click)="openDialog()">
      <mat-icon>visibility</mat-icon>
    </button>`,
  styleUrls: ['./agendamento-detalhes.component.css']
})
export class AgendamentoDetalhesComponent implements OnInit {

  @Input() audiencia: Audiencia;
  @Input() conciliacao: Conciliacao;
  @Input() isAudiencia: boolean;

  constructor(public dialog: MatDialog) { }

  ngOnInit() {}

  private formatarLabelTipoAudiencia(){
    if (this.audiencia.tipoAudiencia === 'ACAO_CIVIL'
        || this.audiencia.tipoAudiencia.toLowerCase() === 'ação civíl'){
      this.audiencia.tipoAudiencia = 'Ação civíl';
    }
    else if (this.audiencia.tipoAudiencia === 'CUSTODIA'
        || this.audiencia.tipoAudiencia.toLowerCase() === 'custódia'){
      this.audiencia.tipoAudiencia = 'Custódia';
    }
    else if (this.audiencia.tipoAudiencia === 'IMPROBIDADE'
        || this.audiencia.tipoAudiencia.toLowerCase() === 'improbidade'){
      this.audiencia.tipoAudiencia = 'Improbidade';
    }
    else if (this.audiencia.tipoAudiencia === 'INSTRUCAO_CRETA'
        || this.audiencia.tipoAudiencia.toLowerCase() === 'instrução do creta'){
      this.audiencia.tipoAudiencia = 'Instrução do creta';
    }
    else if (this.audiencia.tipoAudiencia === 'LEILAO'
        || this.audiencia.tipoAudiencia.toLowerCase() === 'leilão'){
      this.audiencia.tipoAudiencia = 'Leilão';
    }
    else if (this.audiencia.tipoAudiencia === 'OUTROS'
        || this.audiencia.tipoAudiencia.toLowerCase() === 'outros'){
      this.audiencia.tipoAudiencia = 'Outros';
    }
    else if (this.audiencia.tipoAudiencia === 'PENAL'
        || this.audiencia.tipoAudiencia.toLowerCase() === 'penal'){
      this.audiencia.tipoAudiencia = 'Penal';
    }
    else if (this.audiencia.tipoAudiencia === 'PJE'){
      this.audiencia.tipoAudiencia = 'PJE';
    }
    else if (this.audiencia.tipoAudiencia === 'TEBAS_IMPROBIDADE'
        || this.audiencia.tipoAudiencia.toLowerCase() === 'tebas improbidade'){
      this.audiencia.tipoAudiencia = 'Tebas improbidade';
    }
    else {
      this.audiencia.tipoAudiencia = 'Videoconferência';
    }
  }

  openDialog() {

    if (this.audiencia != null){
      this.formatarLabelTipoAudiencia();
    }

    const dialogRef = this.dialog.open(AgendamentoDetalhesDialogComponent, {
      height: '85%',
      data: {
        audiencia: this.audiencia,
        conciliacao: this.conciliacao,
        isAudiencia: this.isAudiencia
      }
    });
  }

}

@Component({
  selector: 'app-agendamento-detalhes-dialog',
  templateUrl: 'agendamento-detalhes-dialog.component.html',
  styleUrls: ['./agendamento-detalhes-dialog.component.css']
})
export class AgendamentoDetalhesDialogComponent implements OnInit {

  colunasExibidas = ['nome', 'email', 'papel']; // 'acoes'

  conciliacao: Conciliacao;
  audiencia: Audiencia;
  isAudiencia: boolean;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {

    this.audiencia = this.data.audiencia;
    this.conciliacao = this.data.conciliacao;
    this.isAudiencia = this.data.isAudiencia;
  }

  ngOnInit() {}

}

export interface ParteInteressada {
  nome: string;
  email: string;
  papel: string;
}
