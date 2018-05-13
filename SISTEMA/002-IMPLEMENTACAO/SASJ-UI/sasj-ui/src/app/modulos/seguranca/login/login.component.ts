import { ErrorHandlerService } from './../../core/error-handler.service';
import { AuthService } from './../auth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  campoMatricula: FormControl;
  campoSenha: FormControl;

  public mascaraMatricula = [/[a-zA-Z]/, /[a-zA-Z]/, '-', /\d/, /\d/, /\d/, /\d/];

  constructor(private auth: AuthService, private router: Router,
      private errorHandler: ErrorHandlerService) {}

  ngOnInit() {
    this.campoMatricula = new FormControl('', [Validators.required]);
    this.campoSenha = new FormControl('', [Validators.required]);
  }

  login() {
    this.auth.login(this.campoMatricula.value, this.campoSenha.value)
      .then(() => {
        this.router.navigate(['/usuarios/novo']);
      })
      .catch(erro => {
        this.errorHandler.handle(erro);
      });
  }

  refresh(): void {
    window.location.reload();
  }

}
