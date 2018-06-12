import { ErrorHandlerService } from './../../core/error-handler.service';
import { RecuperacaoSenhaDialogComponent } from './../../seguranca/login/login.component';
import { AuthService } from './../../seguranca/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { Usuario } from './../../core/model';
import { UsuarioService } from './../usuario.service';
import { Component, OnInit, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { StorageDataService } from '../../core/storage-data.service';

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
  campoSenhaUsuario: FormControl;
  campoTipoUsuario: FormControl;

  public mascaraMatricula = [/[a-zA-Z]/, /[a-zA-Z]/, '-', /\d/, /\d/, /\d/, /\d/];

  tiposUsuarios = ['Padrão', 'Administrador'];

  constructor(private usuarioService: UsuarioService, private router: Router,
      private snackBar: MatSnackBar, private errorHandlerService: ErrorHandlerService,
      private activatedRoute: ActivatedRoute, private authService: AuthService,
      public dialog: MatDialog, private storageDataService: StorageDataService) {
  }

  ngOnInit() {

    const edicaoPerfil = this.router.url === '/perfil';

    if (edicaoPerfil) {

      this.carregarUsuario();

      this.isEdicao = true;
    }

    // console.log(this.usuario);

    this.campoEmail = new FormControl('', [Validators.required, Validators.email]);
    this.campoNome = new FormControl('', [Validators.required]);
    this.campoMatricula = new FormControl('', [Validators.required]);
    this.campoCargo = new FormControl('', [Validators.required]);
    this.campoSenhaUsuario = new FormControl('', [Validators.required]);
    this.campoTipoUsuario = new FormControl('', [Validators.required]);
  }

  salvar() {

    this.usuario.nome = this.campoNome.value;
    this.usuario.matricula = this.campoMatricula.value;
    this.usuario.email = this.campoEmail.value;
    this.usuario.cargo = this.campoCargo.value;

    if (!this.isEdicao) {

      if (this.campoTipoUsuario.value === 'Administrador'){
        this.usuario.tipoUsuario = 'ADMIN';
      }
      else {
        this.usuario.tipoUsuario = 'PADRAO';
      }

      this.usuario.senha = '123456';

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

  atualizarPerfilPessoal() {

    this.usuario.nome = this.campoNome.value;
    this.usuario.matricula = this.campoMatricula.value;
    this.usuario.email = this.campoEmail.value;
    this.usuario.cargo = this.campoCargo.value;

    this.usuarioService.atualizar(this.usuario)
      .then(usuario => {
        this.usuario = usuario;

        this.router.navigate(['/perfil']);

        this.snackBar.open('Perfil atualizado com sucesso!', '', {duration: 4500});
        this.storageDataService.atualizarUsuarioLogado();
      })
      .catch(erro => {
        this.errorHandlerService.handle(erro);
      });

  }

  openDialog() {
    const dialogRef = this.dialog.open(EmailEnviadoDialogComponent, {
      height: '40%',
      data: {
        email: this.usuario.email
      }
    });
  }

  openDialogDesativacao() {
    const dialogRef = this.dialog.open(DesativacaoContaDialogComponent, {
      height: '40%',
      data: {
        usuario: this.usuario
      }
    });
  }

  carregarUsuario(){

    this.usuarioService.listarTodos().then(resultado => {

      const emailUsuarioLogado = this.authService.jwtPayload.user_name;

      this.usuario = resultado.usuarios.filter(filtro => filtro.email === emailUsuarioLogado)[0];

      this.campoNome.setValue(this.usuario.nome);
      this.campoMatricula.setValue(this.usuario.matricula);
      this.campoEmail.setValue(this.usuario.email);
      this.campoTipoUsuario.setValue(this.usuario.tipoUsuario === 'ADMIN' ? 'Administrador' : 'Padrão');
      this.campoCargo.setValue(this.usuario.cargo);
    })
    .catch(erro => this.errorHandlerService.handle(erro));
  }
}

@Component({
  selector: 'app-email-enviado-dialog',
  template: `
  <h2 mat-dialog-title id="titulo-dialog">Redefinição de senha</h2>
  <mat-dialog-content>
    <mat-card class="espacamento-card">
      <mat-card-header>
        <mat-card-subtitle> Um e-mail de redefinição de senha será enviado para seu endereço de e-mail atual </mat-card-subtitle>
      </mat-card-header>

      <mat-card-content>
        <button mat-raised-button color="accent" (click)="enviar()">ENVIAR LINK</button>
      </mat-card-content>

    </mat-card>
  </mat-dialog-content>
  `,
  styleUrls: ['./usuario-cadastro.component.css']

})
export class EmailEnviadoDialogComponent {

  constructor(public dialogRef: MatDialogRef<EmailEnviadoDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public data: any, private authService: AuthService,
      private usuarioService: UsuarioService, private snackBar: MatSnackBar,
      private errorHandlerService: ErrorHandlerService) { }

    enviar() {

      this.authService.login('PP-1234', 'public')
      .then(() => {
        this.usuarioService.recuperarSenha(this.data.email)
          .then(() => {
            this.authService.limparAccessToken();
            this.snackBar.open('E-mail de redefinição de senha enviado', '', { duration: 4500});
          })
          .catch(erro => {
            this.errorHandlerService.handle(erro);
            this.authService.limparAccessToken();
          });
      })
      .catch(erro => {
        this.errorHandlerService.handle(erro);
        this.authService.limparAccessToken();

      });
      this.dialogRef.close();
    }
}

@Component({
  selector: 'app-desativacao-conta-dialog',
  template: `
  <h2 mat-dialog-title id="titulo-dialog">Desativação de conta</h2>
  <mat-dialog-content>
    <mat-card class="espacamento-card">
      <mat-card-header>
        <mat-card-subtitle> Se você desativar sua conta, perderá seu acesso a ela. Deseja continuar? </mat-card-subtitle>
      </mat-card-header>
      <mat-card-content>
        <button class="espacamento-botoes-desativacao"
            mat-raised-button color="warn" (click)="desativar()">DESATIVAR</button>
        <button class="espacamento-botoes-desativacao"
            mat-raised-button (click)="cancelar()">CANCELAR</button>
      </mat-card-content>
    </mat-card>
  </mat-dialog-content>
  `,
  styleUrls: ['./usuario-cadastro.component.css']

})
export class DesativacaoContaDialogComponent {

  constructor(public dialogRef: MatDialogRef<EmailEnviadoDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public data: any, private authService: AuthService,
      private usuarioService: UsuarioService, private snackBar: MatSnackBar,
      private errorHandlerService: ErrorHandlerService, private router: Router) { }

    desativar() {
      this.usuarioService.atualizarAcesso(this.data.usuario);
      this.dialogRef.close();
      this.authService.limparAccessToken();
      this.router.navigate(['/login']);
    }

    cancelar() {
      this.dialogRef.close();
    }
}
