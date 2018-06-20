import { AuthService } from './../seguranca/auth.service';
import { UsuarioService } from './../usuario/usuario.service';
import { Injectable } from '@angular/core';
import { Usuario, Pendencia } from './model';

@Injectable()
export class StorageDataService {

  private filtragem = false;
  private pendencias: Pendencia[];
  usuarioLogado: Usuario;

  constructor(private usuarioService: UsuarioService, private authService: AuthService) {
  }

  /*
    Utilizado para compartilhar informações entre o dialog de busca avançada e a tabela
    de exibição de agendamentos. Esse método é chamado no componente tabela de agendamentos,
    para verificar se uma pesquisa avançada foi concluída. O valor retornado é utilizado
    para exibir/ocultar o botão 'exibir tudo'
  */
  isFiltragemConcluida(): boolean {
    return this.filtragem;
  }

  /*
    Utilizado para compartilhar informações entre o dialog de busca avançada e a tabela
    de exibição de agendamentos. Esse método é chamado no componente de pesquisa avançada,
    quando uma pesquisa avançada é concluída.
  */
  setFiltragemConcluida(isConcluida: boolean): void {
    this.filtragem = isConcluida;
  }

  /*
    Necessário para atualizar as informações do usuário logado na barra de navegação superior,
    cujos dados estavam sendo recuperados do jwtPayload
  */
  atualizarUsuarioLogado() {

    this.usuarioLogado = new Usuario();

    this.usuarioService.listarTodos()
      .then(resultado => {

        const usuarios: Usuario[] = resultado.usuarios;

        const usuario = usuarios.filter(usuarioFiltrado =>
          usuarioFiltrado.email === this.authService.jwtPayload.user_name)[0];

        this.usuarioLogado = usuario;
      });

  }

  setUsuarioLogado(usuario: Usuario) {
    this.usuarioLogado = usuario;
  }

  // /*
  //   Compartilha as pendências criadas no dialog com o componente de cadastro de agendamento
  // */
  // getPendencias(): Pendencia[] {
  //   return this.pendencias;
  // }

  // /*
  //   Compartilha as pendências criadas no dialog com o componente de cadastro de agendamento
  // */
  // setPendencias(pendencias: Pendencia[]) {
  //   this.pendencias = pendencias;
  // }

}

