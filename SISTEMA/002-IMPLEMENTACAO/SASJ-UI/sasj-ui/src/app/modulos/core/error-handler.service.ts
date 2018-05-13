import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable()
export class ErrorHandlerService {

  constructor(public snackBar: MatSnackBar) { }

  handle(errorResponse: any) {
    let msg: string;

    if (typeof errorResponse === 'string') {
      msg = errorResponse;

    } else if (errorResponse instanceof Response
        && errorResponse.status >= 400 && errorResponse.status <= 499) {
      let errors;
      msg = 'Ocorreu um erro ao processar a sua solicitação';

      try {
        errors = errorResponse.json();

        msg = errors[0].mensagemUsuario;
      } catch (e) { }

      console.error('Ocorreu um erro', errorResponse);

    } else {
      msg = 'Erro ao processar serviço remoto. Tente novamente.';
      console.error('Ocorreu um erro', errorResponse);
    }

    this.snackBar.open(msg, '', {panelClass: ['snack-bar-error'], duration: 5500});
  }

}
