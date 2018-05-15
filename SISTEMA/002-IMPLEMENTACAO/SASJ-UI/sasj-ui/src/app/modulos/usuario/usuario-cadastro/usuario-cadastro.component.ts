import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-usuario-cadastro',
  templateUrl: './usuario-cadastro.component.html',
  styleUrls: ['./usuario-cadastro.component.css']
})
export class UsuarioCadastroComponent implements OnInit {

  campoNome: FormControl;
  campoEmail: FormControl;
  campoMatricula: FormControl;
  campoCargo: FormControl;
  campoTipoUsuario: FormControl;

  public mascaraMatricula = [/[a-zA-Z]/, /[a-zA-Z]/, '-', /\d/, /\d/, /\d/, /\d/];

  tiposUsuarios = ['Padrão', 'Administrador'];

  constructor() {}

  ngOnInit() {
    this.campoEmail = new FormControl('', [Validators.required, Validators.email]);
    this.campoNome = new FormControl('', [Validators.required]);
    this.campoMatricula = new FormControl('', [Validators.required]);
    this.campoCargo = new FormControl('', [Validators.required]);
    this.campoTipoUsuario = new FormControl('', [Validators.required]);
  }

}
