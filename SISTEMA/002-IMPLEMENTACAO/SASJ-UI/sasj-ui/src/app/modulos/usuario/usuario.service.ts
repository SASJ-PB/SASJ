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
}
