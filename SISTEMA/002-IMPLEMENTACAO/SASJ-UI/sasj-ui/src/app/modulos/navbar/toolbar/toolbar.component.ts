import { Component, OnInit, ChangeDetectorRef, ViewChild } from '@angular/core';
import { MediaMatcher } from '@angular/cdk/layout';
import { Router } from '@angular/router';

import { MatSidenav } from '@angular/material';

import { LogoutService } from './../../seguranca/logout.service';
import { AuthService } from './../../seguranca/auth.service';
import { ErrorHandlerService } from './../../core/error-handler.service';

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

  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher,
      private authService: AuthService, private logoutService: LogoutService,
      private errorHandlerService: ErrorHandlerService, private router: Router) {

    // se a largura da tela for 700 ou mais, o menu lateral fica fixo e
    // aparece ao lado do conteúdo se não, ele é ocultado e sobrepõe o conteúdo.

    if (this.router.url !== '/login'){

      this.mobileQuery = media.matchMedia('(max-width: 700px)');

      this._mobileQueryListener = () => changeDetectorRef.detectChanges();
      this.mobileQuery.addListener(this._mobileQueryListener);
    }
  }

  ngOnInit() {

    if (this.router.url !== '/login'){
      if (this.mobileQuery.matches) {
        this.sidenavRef.close();
      }
      else {
        this.sidenavRef.open();
      }
    }

  }

  ocultarToolbar() {
    return this.router.url !== '/login';
  }

  logout() {
    this.logoutService.logout()
      .then(() => {
        this.router.navigate(['/login']);
      })
      .catch(erro => this.errorHandlerService.handle(erro));
  }
}
