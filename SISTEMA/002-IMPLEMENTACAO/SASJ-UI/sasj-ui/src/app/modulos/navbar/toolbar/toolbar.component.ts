import { Usuario } from './../../core/model';
import { UsuarioService } from './../../usuario/usuario.service';
import { Component, OnInit, ChangeDetectorRef, ViewChild, AfterViewInit } from '@angular/core';
import { MediaMatcher } from '@angular/cdk/layout';
import { Router } from '@angular/router';

import { MatSidenav } from '@angular/material';

import { LogoutService } from './../../seguranca/logout.service';
import { AuthService } from './../../seguranca/auth.service';
import { ErrorHandlerService } from './../../core/error-handler.service';
import { StorageDataService } from '../../core/storage-data.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  @ViewChild('sidenavRef') public sidenavRef: MatSidenav;

  mobileQuery: MediaQueryList;
  // usuarioLogado: Usuario;

  private _mobileQueryListener: () => void;

  // {label: 'Relatórios', icone: 'description'} {label: 'Início', icone: 'home'},
  opcoesMenuLateralAdmin = [
    {label: 'Usuários', icone: 'supervisor_account', url: '/usuarios'},
    {label: 'Agendamentos', icone: 'calendar_today', url: '/agendamentos'},
  ];

  opcoesMenuLateralPadrao = [
    {label: 'Agendamentos', icone: 'calendar_today', url: '/agendamentos'}
  ];

  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher,
      private authService: AuthService, private logoutService: LogoutService,
      private errorHandlerService: ErrorHandlerService, private router: Router,
      private usuarioService: UsuarioService, private storageDataService: StorageDataService) {

    // se a largura da tela for 700 ou mais, o menu lateral fica fixo e
    // aparece ao lado do conteúdo se não, ele é ocultado e sobrepõe o conteúdo.

    if (this.router.url !== '/login'){

      this.mobileQuery = media.matchMedia('(max-width: 700px)');

      this._mobileQueryListener = () => changeDetectorRef.detectChanges();
      this.mobileQuery.addListener(this._mobileQueryListener);
    }
  }

  ngOnInit() {


    if (this.router.url !== '/'){ // /login
      if (this.mobileQuery.matches) {
        this.sidenavRef.close();
      }
      else {
        this.sidenavRef.open();
      }
    }

  }

  redirecionar(url: string){
    this.router.navigate([url]);
  }

  ocultarToolbar() {
    // console.log(!this.router.url.includes('/recuperacao/senha/'));
    return this.router.url !== '/login' && !this.router.url.includes('/recuperacao/senha/');
  }

  logout() {
    this.logoutService.logout()
      .then(() => {
        this.router.navigate(['/login']);
        this.storageDataService.usuarioLogado = null;
      })
      .catch(erro => this.errorHandlerService.handle(erro));
  }
}
