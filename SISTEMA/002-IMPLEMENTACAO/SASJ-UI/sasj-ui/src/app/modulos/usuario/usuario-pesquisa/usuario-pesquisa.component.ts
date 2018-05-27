import { FormControl, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material';
import { AuthService } from './../../seguranca/auth.service';
import { Router } from '@angular/router';
import { UsuarioService } from './../usuario.service';
import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../core/model';
import { ErrorHandlerService } from '../../core/error-handler.service';

@Component({
  selector: 'app-usuario-pesquisa',
  templateUrl: './usuario-pesquisa.component.html',
  styleUrls: ['./usuario-pesquisa.component.css']
})
export class UsuarioPesquisaComponent implements OnInit {

  dataSource: MatTableDataSource<Usuario>;
  checkboxStatus: FormControl;
  colunasExibidas = ['nome', 'tipo', 'status', 'acoes'];

  constructor(private usuarioService: UsuarioService, private router: Router,
      private errorHandlerService: ErrorHandlerService, private authService: AuthService) { }

  ngOnInit() {
    this.listarUsuarios();
  }

  atualizarAcesso(indiceLinha: number) {

    // console.log(this.dataSource.filteredData[indiceLinha]);
    // _updateChangeSubscription ATUALIZAR DADOS TABELA

    const usuario = this.dataSource.filteredData[indiceLinha];

    this.usuarioService.atualizarAcesso(usuario)
      .then(usuarioAlterado => {
        this.dataSource._updateChangeSubscription();
        // this.listarAudiencias();

      })
      .catch(erro => {
        this.errorHandlerService.handle(erro);
      });
  }

  listarUsuarios() {
    this.usuarioService.listarTodos()
      .then(resultado => {

        const usuarios: Usuario[] = resultado.usuarios;

        // Removendo usuário publico
        const usuarioPublico: Usuario = usuarios.filter(usuarioFiltrado =>
            usuarioFiltrado.nome === 'Public')[0];

        usuarios.splice(usuarios.indexOf(usuarioPublico), 1);

        // Removendo usuário logado
        const usuarioLogado: Usuario = usuarios.filter(usuarioFiltrado =>
          usuarioFiltrado.email === this.authService.jwtPayload.user_name)[0];

        usuarios.splice(usuarios.indexOf(usuarioLogado), 1);

        this.dataSource = new MatTableDataSource(usuarios);
      });
  }
}
