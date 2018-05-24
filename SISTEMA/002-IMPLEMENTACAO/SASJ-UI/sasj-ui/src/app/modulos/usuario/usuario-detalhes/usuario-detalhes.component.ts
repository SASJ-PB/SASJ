import { Usuario } from './../../core/model';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { Component, OnInit, Inject, Input } from '@angular/core';

@Component({
  selector: 'app-usuario-detalhes',
  template: `
    <button mat-icon-button (click)="openDialog()">
      <mat-icon>visibility</mat-icon>
    </button>`,
  styleUrls: ['./usuario-detalhes.component.css']
})
export class UsuarioDetalhesComponent implements OnInit {

  @Input() usuario: Usuario;

  constructor(public dialog: MatDialog) { }

  openDialog() {
      const dialogRef = this.dialog.open(UsuarioDetalhesDialogComponent, {
        height: '85%',
        data: {
          usuario: this.usuario
        }
      });
    }

  ngOnInit() {
  }

}

@Component({
  selector: 'app-usuario-detalhes-dialog',
  templateUrl: 'usuario-detalhes-dialog.component.html',
  styleUrls: ['./usuario-detalhes-dialog.component.css']
})
export class UsuarioDetalhesDialogComponent implements OnInit {

  usuario: Usuario;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {

    this.usuario = this.data.usuario;
  }

  ngOnInit() {}

}
