import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-loading',
  template: `

  <h2 mat-dialog-title id="titulo-dialog">PROCESSANDO SOLICITAÇÃO</h2>
  <mat-dialog-content>

    <div style="margin: auto; margin-top: 10%; margin-bottom: 10%;">
      <mat-spinner style="margin: auto"></mat-spinner>
    </div>

  </mat-dialog-content>
  `,
  styles: ['']
})
export class LoadingComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
