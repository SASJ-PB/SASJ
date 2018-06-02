export class Usuario {
  codigo: number;
  nome: string;
  cargo: string;
  matricula: string;
  senha: string;
  email: string;
  tipoUsuario: string;
  ativo: boolean;
}

export class Processo {
  codigo: number;
  numeroProcesso: string;
  nomeDaParte: string;
}

// export enum TipoAudiencia{
//   PENAL, ACAO_CIVIL, IMPROBIDADE, INSTRUCAO_CRETA, LEILAO,
// 	PJE,
// 	TEBAS_IMPROBIDADE,
// 	VIDEOCONFERENCIA,
// 	OUTROS;
// }

/*export abstract class SessaoJuridica {
  codigo: number;
  quantidadeOitivas: number;
  observacao: string;
  duracaoEstimada: number;
  processo: Processo;
}*/

export class Audiencia{
  tipoAudiencia: string;
  codigo: number;
  quantidadeOitivas: number;
  observacao: string;
  duracaoEstimada: number;
  processo: Processo;
  agendamento: string;
  statusAgendamento: string;
}

export class AudienciaFilter{
  dataAgendamentoDe: string;
  dataAgendamentoAte: string;
  statusAgendamento: string;
  tipoAudiencia: string;
  quantidadeOitivasDe: string;
  quantidadeOitivasAte: string;
  duracaoEstimadaDe: string;
  duracaoEstimadaAte: string;
  observacao: string;
  numeroProcesso: string;
  nomeDaParteProcesso: string;
}

export class Conciliacao {
  nomeConciliador: string;
  codigo: number;
  quantidadeOitivas: number;
  observacao: string;
  duracaoEstimada: number;
  processo: Processo;
  agendamento: string;
  statusAgendamento: string;
}

export class ConciliacaoFilter{
  dataAgendamentoDe: string;
  dataAgendamentoAte: string;
  statusAgendamento: string;
  nomeConciliador: string;
  quantidadeOitivasDe: string;
  quantidadeOitivasAte: string;
  duracaoEstimadaDe: string;
  duracaoEstimadaAte: string;
  observacao: string;
  numeroProcesso: string;
  nomeDaParteProcesso: string;
}
