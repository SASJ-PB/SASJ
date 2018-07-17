import { ErrorHandlerService } from './../../core/error-handler.service';
import { RecuperacaoSenhaDialogComponent } from './../../seguranca/login/login.component';
import { AuthService } from './../../seguranca/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { Usuario } from './../../core/model';
import { UsuarioService } from './../usuario.service';
import { Component, OnInit, Inject } from '@angular/core';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { StorageDataService } from '../../core/storage-data.service';
import { LoadingComponent } from '../../core/loading.component';

@Component({
  selector: 'app-usuario-cadastro',
  templateUrl: './usuario-cadastro.component.html',
  styleUrls: ['./usuario-cadastro.component.css']
})
export class UsuarioCadastroComponent implements OnInit {

  isEdicao = false;
  usuario = new Usuario();
  usuarioForm: FormGroup;

  public mascaraMatricula = [/[a-zA-Z]/, /[a-zA-Z]/, '-', /\d/, /\d/, /\d/, /\d/];

  tiposUsuarios = ['Administrador', 'Padrão'];

  constructor(private usuarioService: UsuarioService, private router: Router,
      private snackBar: MatSnackBar, private errorHandlerService: ErrorHandlerService,
      private activatedRoute: ActivatedRoute, private authService: AuthService,
      public dialog: MatDialog, private storageDataService: StorageDataService,
      private formBuilder: FormBuilder)
  {
    this.createForm();
  }

  ngOnInit()
  {
    const edicaoPerfil = this.router.url === '/perfil';

    if (edicaoPerfil) {

      this.carregarUsuario();

      this.isEdicao = true;
    }
  }

  private createForm()
  {
    this.usuarioForm = this.formBuilder.group({
      campoEmail: [null, [Validators.required, Validators.email]],
      campoNome: [null, Validators.required],
      campoMatricula: [null, [Validators.required]],
      campoCargo: [null, [Validators.required]],
      campoTipoUsuario: ['Administrador', [Validators.required]]
    });
  }

  salvar()
  {
    this.usuario.nome = this.usuarioForm.get('campoNome').value;
    this.usuario.matricula = this.usuarioForm.get('campoMatricula').value;
    this.usuario.email = this.usuarioForm.get('campoEmail').value;
    this.usuario.cargo = this.usuarioForm.get('campoCargo').value;

    if (!this.isEdicao) {

      if (this.usuarioForm.get('campoTipoUsuario').value === 'Administrador'){
        this.usuario.tipoUsuario = 'ADMIN';
      }
      else {
        this.usuario.tipoUsuario = 'PADRAO';
      }

      this.usuario.senha = '123456';

      if (this.usuarioForm.status !== 'INVALID') {

        this.dialog.open(LoadingComponent, { disableClose: true });

        this.usuarioService.cadastrar(this.usuario)
        .then((usuarioAdicionado) => {

          this.router.navigate(['/usuarios']);

          this.dialog.closeAll();

          this.snackBar.open('Usuário cadastrado com sucesso', '', { duration: 4500});
        })
        .catch(erro => {
          this.dialog.closeAll();
          this.errorHandlerService.handle(erro);
        });
      }

    }
    else{
      this.atualizarPerfilPessoal();
    }
  }

  atualizarPerfilPessoal()
  {
    this.usuario.nome = this.usuarioForm.get('campoNome').value;
    this.usuario.matricula = this.usuarioForm.get('campoMatricula').value;
    this.usuario.email = this.usuarioForm.get('campoEmail').value;
    this.usuario.cargo = this.usuarioForm.get('campoCargo').value;

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

  carregarUsuario()
  {
    this.usuarioService.listarTodos().then(resultado => {

      const emailUsuarioLogado = this.authService.jwtPayload.user_name;

      this.usuario = resultado.usuarios.filter(filtro => filtro.email === emailUsuarioLogado)[0];

      this.usuarioForm.setValue({
        campoNome: this.usuario.nome,
        campoMatricula: this.usuario.matricula,
        campoEmail: this.usuario.email,
        campoCargo: this.usuario.cargo,
        campoTipoUsuario: this.usuario.tipoUsuario === 'ADMIN' ? 'Administrador' : 'Padrão',
      });
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
      private errorHandlerService: ErrorHandlerService, private dialog: MatDialog,
      private router: Router) {
  }

  enviar()
  {
    this.dialogRef.close();

    this.dialog.open(LoadingComponent, { disableClose: true });

    this.authService.login('PP-1234', 'public')
    .then(() => {
      this.usuarioService.recuperarSenha(this.data.email)
        .then(() => {
          this.authService.limparAccessToken();
          this.router.navigate(['/login']);
          this.snackBar.open('E-mail de redefinição de senha enviado', '', { duration: 4500});
        })
        .catch(erro => {
          this.errorHandlerService.handle(erro);
          this.authService.limparAccessToken();
        });

        this.dialog.closeAll();
    })
    .catch(erro => {
      this.errorHandlerService.handle(erro);
      this.authService.limparAccessToken();
    });
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
        <button id="confirmarDesativacao" class="espacamento-botoes-desativacao"
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
