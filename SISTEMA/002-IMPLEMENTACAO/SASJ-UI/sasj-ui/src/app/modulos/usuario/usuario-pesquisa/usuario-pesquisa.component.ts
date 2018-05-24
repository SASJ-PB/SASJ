import { MatTableDataSource } from '@angular/material';
import { AuthService } from './../../seguranca/auth.service';
import { Router } from '@angular/router';
import { UsuarioService } from './../usuario.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-usuario-pesquisa',
  templateUrl: './usuario-pesquisa.component.html',
  styleUrls: ['./usuario-pesquisa.component.css']
})
export class UsuarioPesquisaComponent implements OnInit {

  dataSource;

  colunasExibidas = ['nome', 'tipo', 'status', 'acoes'];

  constructor(private usuarioService: UsuarioService,
      private authService: AuthService, private router: Router) { }

  ngOnInit() {
    this.listarAudiencias();
  }

  listarAudiencias() {
    this.usuarioService.listarTodos()
      .then(resultado => {

        this.dataSource = new MatTableDataSource(resultado.usuarios);
      });
  }
}
