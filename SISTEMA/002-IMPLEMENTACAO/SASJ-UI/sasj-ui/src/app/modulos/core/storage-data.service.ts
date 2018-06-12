import { AuthService } from './../seguranca/auth.service';
import { UsuarioService } from './../usuario/usuario.service';
import { Injectable } from '@angular/core';
import { Usuario } from './model';

@Injectable()
export class StorageDataService {

  private filtragem = false;
  usuarioLogado: Usuario = new Usuario();

  constructor(private usuarioService: UsuarioService, private authService: AuthService) {
  }

  isFiltragemConcluida(): boolean {
    return this.filtragem;
  }

  setFiltragemConcluida(isConcluida: boolean): void {
    this.filtragem = isConcluida;
  }

  atualizarUsuarioLogado() {

    if (this.usuarioLogado.codigo){
      this.usuarioService.buscarPorCodigo(this.usuarioLogado.codigo)
        .then(resultado => {
          this.usuarioLogado = resultado;
        });
    }
    else{
      this.usuarioService.listarTodos()
        .then(resultado => {

          const usuarios: Usuario[] = resultado.usuarios;

          const usuario = usuarios.filter(usuarioFiltrado =>
            usuarioFiltrado.email === this.authService.jwtPayload.user_name)[0];

            this.usuarioLogado = usuario;
        });
    }
  }

}

