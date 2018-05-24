import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { Usuario } from './../../core/model';
import { UsuarioService } from './../usuario.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ErrorHandlerService } from '../../core/error-handler.service';

@Component({
  selector: 'app-usuario-cadastro',
  templateUrl: './usuario-cadastro.component.html',
  styleUrls: ['./usuario-cadastro.component.css']
})
export class UsuarioCadastroComponent implements OnInit {

  isEdicao = false;
  usuario = new Usuario();

  campoNome: FormControl;
  campoEmail: FormControl;
  campoMatricula: FormControl;
  campoCargo: FormControl;
  campoTipoUsuario: FormControl;

  public mascaraMatricula = [/[a-zA-Z]/, /[a-zA-Z]/, '-', /\d/, /\d/, /\d/, /\d/];

  tiposUsuarios = ['Padrão', 'Administrador'];

  constructor(private usuarioService: UsuarioService, private router: Router,
      private snackBar: MatSnackBar, private errorHandlerService: ErrorHandlerService,
      private activatedRoute: ActivatedRoute) {

    const codigoUsuario = this.activatedRoute.snapshot.params['codigo'];

    if (codigoUsuario){
      this.carregarUsuario(codigoUsuario);
      this.isEdicao = true;
    }
  }

  ngOnInit() {
    this.campoEmail = new FormControl('', [Validators.required, Validators.email]);
    this.campoNome = new FormControl('', [Validators.required]);
    this.campoMatricula = new FormControl('', [Validators.required]);
    this.campoCargo = new FormControl('', [Validators.required]);
    this.campoTipoUsuario = new FormControl('', [Validators.required]);
  }

  salvar() {

    this.usuario.nome = this.campoNome.value;
    this.usuario.matricula = this.campoMatricula.value;
    this.usuario.email = this.campoEmail.value;
    this.usuario.cargo = this.campoCargo.value;
    this.usuario.senha = '123456';

    if (this.campoCargo.value === 'Administrador'){
      this.usuario.tipoUsuario = 'ADMIN';
    }
    else {
      this.usuario.tipoUsuario = 'PADRAO';
    }

    if (!this.isEdicao) {
      this.usuarioService.cadastrar(this.usuario)
      .then((usuarioAdicionado) => {
        this.router.navigate(['/usuarios']);
        this.snackBar.open('Usuário cadastrado com sucesso', '', { duration: 4500});
      })
      .catch(erro => {
        this.errorHandlerService.handle(erro);
      });
    }
    else{
      this.atualizarPerfilPessoal();
    }
  }

  atualizarAcesso() {

  }

  atualizarTipoUsuario() {

  }

  atualizarPerfilPessoal() {

    this.usuario.nome = this.campoNome.value;
    this.usuario.matricula = this.campoMatricula.value;
    this.usuario.email = this.campoEmail.value;
    this.usuario.tipoUsuario = this.campoTipoUsuario.value;
    this.usuario.cargo = this.campoCargo.value;

    this.usuarioService.atualizar(this.usuario)
      .then(usuario => {
        this.usuario = usuario;

        this.router.navigate(['/perfil']);

        this.snackBar.open('Perfil atualizado com sucesso!', '', {duration: 4500});
      })
      .catch(erro => {
        this.errorHandlerService.handle(erro);
      });
  }

  carregarUsuario(codigo: number){
    this.usuarioService.buscarPorCodigo(codigo)
      .then(usuario => {
        this.usuario = usuario;

        this.campoNome.setValue(this.usuario.nome);
        this.campoMatricula.setValue(this.usuario.matricula);
        this.campoEmail.setValue(this.usuario.email);
        this.campoTipoUsuario.setValue(this.usuario.tipoUsuario);
        this.campoCargo.setValue(this.usuario.cargo);

      })
      .catch(erro => this.errorHandlerService.handle(erro));
  }
}
