import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './../../seguranca/auth.service';
import { UsuarioService } from './../usuario.service';
import { ErrorHandlerService } from '../../core/error-handler.service';
import { Usuario } from '../../core/model';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-usuario-pesquisa',
  templateUrl: './usuario-pesquisa.component.html',
  styleUrls: ['./usuario-pesquisa.component.css']
})
export class UsuarioPesquisaComponent implements OnInit {

  @ViewChild('sortUser') sort: MatSort;

  dataSource: MatTableDataSource<Usuario> = new MatTableDataSource();
  colunasExibidas = ['nome', 'tipoUsuario', 'ativo', 'acoes'];

  constructor(private usuarioService: UsuarioService, private router: Router,
      private errorHandlerService: ErrorHandlerService, private authService: AuthService) { }

  ngOnInit() {
    this.listarUsuarios();
  }

  atualizarAcesso(usuario: Usuario) {

    const usuarios = this.dataSource.data;

    usuarios.forEach(user => {

      if (user.codigo === usuario.codigo){

        this.usuarioService.atualizarAcesso(usuario)
          .then(usuarioAlterado => {
            this.dataSource._updateChangeSubscription();

          })
          .catch(erro => {
            this.errorHandlerService.handle(erro);
          });
      }
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

        this.dataSource.data = usuarios;
        this.dataSource.sort = this.sort;
      });
  }
}
