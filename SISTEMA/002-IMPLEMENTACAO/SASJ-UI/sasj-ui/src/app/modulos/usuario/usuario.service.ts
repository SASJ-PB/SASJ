import { environment } from './../../../environments/environment';
import { Usuario } from './../core/model';
import { Injectable } from '@angular/core';
import { AuthHttp } from 'angular2-jwt';
import { AuthService } from './../seguranca/auth.service';

@Injectable()
export class UsuarioService {

  usuariosUrl: string;
  recuperacaoUrl: string;

  constructor(private http: AuthHttp, private authService: AuthService) {

    this.usuariosUrl = `${environment.apiUrl}/usuarios`;
    this.recuperacaoUrl = `${environment.apiUrl}/recuperacao`;
  }

  recuperarSenha(email: string): Promise<any> {
    return this.http.put(`${this.recuperacaoUrl}/senha/?email=${email}`, {})
      .toPromise()
      .then(response => {

      });
  }

  atualizarSenha(usuario: Usuario): Promise<Usuario> {
    return this.http.put(`${this.recuperacaoUrl}/usuario/${usuario.codigo}`,
      JSON.stringify(usuario))
      .toPromise()
      .then(response => {
        const usuarioAlterado = response.json() as Usuario;
        return usuarioAlterado;
      });
  }

  buscarUsuarioPorToken(token: string): Promise<Usuario> {
    return this.http.get(`${this.recuperacaoUrl}/${token}`)
      .toPromise()
      .then(response => {
        const usuario = response.json() as Usuario;
        return usuario;
      });
  }

  cadastrar(usuario: Usuario): Promise<Usuario> {
    return this.http.post(this.usuariosUrl, JSON.stringify(usuario))
      .toPromise()
      .then(response => response.json());
  }

  buscarPorCodigo(codigo: number): Promise<Usuario> {
    return this.http.get(`${this.usuariosUrl}/${codigo}`)
      .toPromise()
      .then(response => {

        const usuario = response.json() as Usuario;

        return usuario;
      });
  }

  atualizar(usuario: Usuario): Promise<Usuario> {
    return this.http.put(`${this.usuariosUrl}/${usuario.codigo}`,
        JSON.stringify(usuario))
      .toPromise()
      .then(response => {
        const usuarioAlterado = response.json() as Usuario;
        return usuarioAlterado;
      });
  }

  atualizarAcesso(usuario: Usuario): Promise<Usuario> {
    return this.http.put(`${this.usuariosUrl}/${usuario.codigo}/ativo`, !usuario.ativo)
      .toPromise().then(() => null);
  }

  listarTodos(): Promise<any> {
    return this.http.get(`${this.usuariosUrl}/`)
      .toPromise()
      .then(response => {

        const resultado = {
          usuarios: response.json(),
        };

        return resultado;
      });
  }
}
