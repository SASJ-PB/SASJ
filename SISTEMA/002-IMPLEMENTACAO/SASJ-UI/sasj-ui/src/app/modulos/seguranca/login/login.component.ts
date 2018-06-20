import { StorageDataService } from './../../core/storage-data.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material';
import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { Usuario } from './../../core/model';
import { ErrorHandlerService } from './../../core/error-handler.service';
import { AuthService } from './../auth.service';
import { UsuarioService } from './../../usuario/usuario.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  campoMatricula: FormControl;
  campoSenha: FormControl;

  public mascaraMatricula = [/[a-zA-Z]/, /[a-zA-Z]/, '-', /\d/, /\d/, /\d/, /\d/];

  constructor(private authService: AuthService, private router: Router,
      private errorHandlerService: ErrorHandlerService, public dialog: MatDialog,
      private storageDataService: StorageDataService) {}

  ngOnInit() {
    this.campoMatricula = new FormControl('', [Validators.required]);
    this.campoSenha = new FormControl('', [Validators.required]);
  }

  login() {
    this.authService.login(this.campoMatricula.value, this.campoSenha.value)
      .then(() => {
        this.router.navigate(['/agendamentos']);
        this.storageDataService.atualizarUsuarioLogado();
      })
      .catch(erro => {
        this.errorHandlerService.handle(erro);
      });
  }

  openDialog() {
    const dialogRef = this.dialog.open(RecuperacaoSenhaDialogComponent, {
      height: '60%'
    });
  }

}

@Component({
  selector: 'app-recuperacao-senha-dialog',
  templateUrl: 'recuperacao-senha-dialog.component.html',
  styleUrls: ['./recuperacao-senha-dialog.component.css']
})
export class RecuperacaoSenhaDialogComponent implements OnInit {

  constructor(private authService: AuthService, private errorHandlerService: ErrorHandlerService,
      private usuarioService: UsuarioService, public snackBar: MatSnackBar, public dialog: MatDialog) {
  }

  campoEmailRecuperacao: FormControl;

  ngOnInit() {
    this.campoEmailRecuperacao = new FormControl('', [Validators.required, Validators.email]);
  }

  recuperar() {
    this.authService.login('PP-1234', 'public')
    .then(() => {
      this.usuarioService.recuperarSenha(this.campoEmailRecuperacao.value)
        .then(() => {
          this.authService.limparAccessToken();
          this.dialog.closeAll();
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
  }

}

@Component({
  selector: 'app-redefinicao-senha',
  templateUrl: 'redefinicao-senha.component.html',
  styleUrls: ['./redefinicao-senha.component.css']
})
export class RedefinicaoSenhaComponent implements OnInit {

  campoNovaSenha: FormControl;
  campoConfirmacaoNovaSenha: FormControl;
  usuario = new Usuario();

  constructor(private authService: AuthService, private errorHandlerService: ErrorHandlerService,
      private usuarioService: UsuarioService, public snackBar: MatSnackBar, private router: Router,
      public dialog: MatDialog, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    const tokenRecuperação = this.activatedRoute.snapshot.params['token'];
    this.usuario.codigo = 0;

    if (tokenRecuperação) {
      this.buscarUsuarioPorToken(tokenRecuperação);
    }

    this.campoNovaSenha = new FormControl('', [Validators.required]);
    this.campoConfirmacaoNovaSenha = new FormControl('', [Validators.required]);
  }

  verificarSenhasDiferentes() {
    if (this.campoNovaSenha.value === this.campoConfirmacaoNovaSenha.value) {
      return false;
    }
    return true;
  }

  isSenhasDiferentes() {
    return this.verificarSenhasDiferentes();
  }

  buscarUsuarioPorToken(tokenRecuperação: string) {
    this.authService.login('PP-1234', 'public')
      .then(() => {
        this.usuarioService.buscarUsuarioPorToken(tokenRecuperação)
          .then(usuario => {
            this.authService.limparAccessToken();
            this.usuario = usuario;
            if (usuario.codigo === 0) {
              this.router.navigate(['/login']);
            }
          })
          .catch(erro => {
            this.authService.limparAccessToken();
            this.router.navigate(['/login']);
          });
      })
      .catch(erro => {
        this.errorHandlerService.handle(erro);
        this.authService.limparAccessToken();
      });

  }

  redefinir() {
    this.authService.login('PP-1234', 'public')
      .then(() => {
        this.usuario.senha = this.campoNovaSenha.value;
        this.usuarioService.atualizarSenha(this.usuario)
          .then(() => {
            this.authService.limparAccessToken();
            this.router.navigate(['/login']);
            this.snackBar.open('Senha alterada com sucesso!', '', { duration: 4500});
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
  }

}
