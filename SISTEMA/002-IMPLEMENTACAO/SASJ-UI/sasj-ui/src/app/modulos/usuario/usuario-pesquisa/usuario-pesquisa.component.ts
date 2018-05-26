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

  dataSource;
  checkboxStatus: FormControl;
  colunasExibidas = ['nome', 'tipo', 'status', 'acoes'];

  constructor(private usuarioService: UsuarioService, private router: Router,
      private errorHandlerService: ErrorHandlerService, private authService: AuthService) { }

  ngOnInit() {
    this.listarAudiencias();
  }

  atualizarAcesso(indiceLinha: number) {

    // console.log(this.dataSource.filteredData[indiceLinha]);
    // _updateChangeSubscription ATUALIZAR DADOS TABELA

    const usuario = this.dataSource.filteredData[indiceLinha];

    this.usuarioService.atualizarAcesso(usuario)
      .then(usuarioAlterado => {
        // this.dataSource = null;
        // this.listarAudiencias();

      })
      .catch(erro => {
        this.errorHandlerService.handle(erro);
      });
  }

  listarAudiencias() {
    this.usuarioService.listarTodos()
      .then(resultado => {

        this.dataSource = new MatTableDataSource(resultado.usuarios);
      });
  }
}
