import { environment } from './../../../environments/environment';
import { JwtHelper } from 'angular2-jwt';
import { Http, Headers } from '@angular/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

@Injectable()
export class AuthService {

  oauthTokenUrl: string;
  jwtPayload: any;

  constructor(private http: Http, private jwtHelper: JwtHelper) {
    this.oauthTokenUrl = `${environment.apiUrl}/oauth/token`;
    this.carregarToken();
  }

  temPermissao(permissao: string) {
    return this.jwtPayload && this.jwtPayload.authorities.includes(permissao);
  }

  temQualquerPermissao(roles) {

    for (const role of roles) {
      if (this.temPermissao(role)){
        return true;
      }
    }
    return false;
  }

  limparAccessToken() {
    localStorage.removeItem('token');
    this.jwtPayload = null;
  }

  login(matricula: string, senha: string): Promise<void> {
    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('Authorization', 'Basic YW5ndWxhcjpAbmd1bEBy');

    const body = `username=${matricula}&password=${senha}&grant_type=password`;

    return this.http.post(this.oauthTokenUrl, body, { headers, withCredentials: true })
      .toPromise()
      .then(response => {
        this.armazenarToken(response.json().access_token);
      })
      .catch(response => {

        if (response.status === 400){
          const responseJson = response.json();

          if (responseJson.error === 'invalid_grant'){
            return Promise.reject('Matrícula ou senha inválida !');
          }
        }
        return Promise.reject(response);
      });
  }

  private armazenarToken(token: string){
    this.jwtPayload = this.jwtHelper.decodeToken(token);
    localStorage.setItem('token', token);
  }

  private carregarToken() {
    const token = localStorage.getItem('token');

    if (token){
      this.armazenarToken(token);
    }
  }

  obterNovoAccessToken(): Promise<void> {

    const headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('Authorization', 'Basic YW5ndWxhcjpAbmd1bEBy');

    const body = 'grant_type=refresh_token';

    return this.http.post(this.oauthTokenUrl, body, { headers, withCredentials: true })
      .toPromise()
      .then(response => {

        this.armazenarToken(response.json().access_token);

        console.log('Novo Access token criado');

        return Promise.resolve(null);
      })
      .catch(response => {
        console.error('Erro ao renovar token', response);

        return Promise.resolve(null);
      });
  }

  isAccessTokenInvalido() {
    const token = localStorage.getItem('token');

    return !token || this.jwtHelper.isTokenExpired(token);
  }

}
