import { MediaMatcher } from '@angular/cdk/layout';
import { Component, OnInit, ChangeDetectorRef, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  @ViewChild('sidenavRef') public sidenavRef: MatSidenav;

  mobileQuery: MediaQueryList;

  private _mobileQueryListener: () => void;

  opcoesMenuLateral = [
    {label: 'Início', icone: 'home'},
    {label: 'Agendamentos', icone: 'calendar_today'},
    {label: 'Relatórios', icone: 'description'}
  ];

  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher) {

    // se a largura da tela for 700 ou mais, o menu lateral fica fixo e
    // aparece ao lado do conteúdo se não, ele é ocultado e sobrepõe o conteúdo.
    this.mobileQuery = media.matchMedia('(max-width: 700px)');

    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnInit() {

    if (this.mobileQuery.matches) {
      this.sidenavRef.close();
    }
    else {
      this.sidenavRef.open();
    }
  }
}
