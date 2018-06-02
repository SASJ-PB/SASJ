import { Injectable } from '@angular/core';

@Injectable()
export class StorageDataService {

  private filtragem = false;

  isFiltragemConcluida(): boolean {
    return this.filtragem;
  }

  setFiltragemConcluida(isConcluida: boolean): void {
    this.filtragem = isConcluida;
  }
}
