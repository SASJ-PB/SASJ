import { Usuario } from './../core/model';
import { Injectable } from '@angular/core';
import { AuthHttp } from 'angular2-jwt';
import { AuthService } from './../seguranca/auth.service';

@Injectable()
export class UsuarioService {

  constructor(private http: AuthHttp, private authService: AuthService) { }

  recuperarSenha(email: string): Promise<any> {
    return this.http.put(`http://localhost:8080/recuperacao/senha/?email=${email}`, {})
      .toPromise()
      .then(response => {

      });
  }

  atualizarSenha(usuario: Usuario): Promise<Usuario> {
    return this.http.put(`http://localhost:8080/recuperacao/usuario/${usuario.codigo}`,
      JSON.stringify(usuario))
      .toPromise()
      .then(response => {
        const usuarioAlterado = response.json() as Usuario;
        return usuarioAlterado;
      });
  }

  buscarUsuarioPorToken(token: string): Promise<Usuario> {
    return this.http.get(`http://localhost:8080/recuperacao/${token}`)
      .toPromise()
      .then(response => {
        const usuario = response.json() as Usuario;
        return usuario;
      });
  }

  cadastrar(usuario: Usuario): Promise<Usuario> {
    return this.http.post('http://localhost:8080/usuarios', JSON.stringify(usuario))
      .toPromise()
      .then(response => response.json());
  }

  buscarPorCodigo(codigo: number): Promise<Usuario> {
    return this.http.get(`http://localhost:8080/usuarios/${codigo}`)
      .toPromise()
      .then(response => {

        const usuario = response.json() as Usuario;

        return usuario;
      });
  }

  atualizar(usuario: Usuario): Promise<Usuario> {
    return this.http.put(`http://localhost:8080/usuarios/${usuario.codigo}`,
        JSON.stringify(usuario))
      .toPromise()
      .then(response => {
        const usuarioAlterado = response.json() as Usuario;
        return usuarioAlterado;
      });
  }

  atualizarAcesso(usuario: Usuario): Promise<Usuario> {
    // console.log(usuario.ativo);
    return this.http.put(`http://localhost:8080/usuarios/${usuario.codigo}/ativo`, !usuario.ativo)
      .toPromise().then(() => null);
  }

  listarTodos(): Promise<any> {
    return this.http.get(`http://localhost:8080/usuarios/`)
      .toPromise()
      .then(response => {

        const resultado = {
          usuarios: response.json(),
        };

        return resultado;
      });
  }
}
