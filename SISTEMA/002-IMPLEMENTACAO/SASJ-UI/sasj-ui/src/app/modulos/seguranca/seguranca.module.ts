import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Http, RequestOptions } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatDialogModule } from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { AuthHttp, AuthConfig } from 'angular2-jwt';
import { TextMaskModule } from 'angular2-text-mask';

import { SegurancaRoutingModule } from './seguranca-routing.module';
import { LoginComponent, RecuperacaoSenhaDialogComponent, RedefinicaoSenhaComponent } from './login/login.component';
import { AuthGuard } from './auth.guard';
import { AuthService } from './auth.service';
import { SasjHttp } from './sasj-http';
import { LogoutService } from './logout.service';

export function authHttpServiceFactory(authService: AuthService, http: Http, options: RequestOptions) {
  const config = new AuthConfig({
    globalHeaders: [
      { 'Content-Type': 'application/json' }
    ]
  });

  return new SasjHttp(authService, config , http, options);
}

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,

    SegurancaRoutingModule,

    MatCardModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDialogModule,

    TextMaskModule
  ],
  declarations: [LoginComponent, RecuperacaoSenhaDialogComponent, RedefinicaoSenhaComponent],
  exports: [LoginComponent, RecuperacaoSenhaDialogComponent, RedefinicaoSenhaComponent],
  providers: [
    {
      provide: AuthHttp,
      useFactory: authHttpServiceFactory,
      deps: [AuthService, Http, RequestOptions]
    },
    AuthGuard,
    LogoutService
  ],
  entryComponents: [
    RecuperacaoSenhaDialogComponent
  ],
})
/* {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 2500}} */
export class SegurancaModule { }
