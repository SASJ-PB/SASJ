package br.edu.ifpb.monteiro.ads.sasj.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.StatusAgendamento;
import br.edu.ifpb.monteiro.ads.sasj.api.model.SessaoJuridica;

@Service
public class NotificacaoService {

	@Autowired
	private AgendamentoService agendamentoService;

	public void notificarPartesSobreConfirmacao(SessaoJuridica sessaoJuridica) {
		agendamentoService.notificarPartesSobreConfirmacao(sessaoJuridica);
	}

	public void notificarPartesSobreReagendamento(SessaoJuridica sessaoJuridica) {
		agendamentoService.notificarPartesSobreReagendamento(sessaoJuridica);
	}

	public void notificarPartesSobreAdiamento(SessaoJuridica sessaoJuridica) {
		System.out.println("--------------------------NOTIFICANDO SOBRE ADIAMENTO--------------------------");
	}

	public void notificarPartesSobreCancelamento(SessaoJuridica sessaoJuridica) {
		System.out.println("--------------------------NOTIFICANDO SOBRE CANCELAMENTO--------------------------");
	}

	public boolean isConfirmado(SessaoJuridica sessaoJuridica, SessaoJuridica sessaoJuridicaSalva) {
		if (sessaoJuridica.getStatusAgendamento() != sessaoJuridicaSalva.getStatusAgendamento()) {
			if (sessaoJuridica.getStatusAgendamento() == StatusAgendamento.CONFIRMADO) {
				return true;
			}
		}
		return false;
	}

	public boolean isReagendado(SessaoJuridica sessaoJuridica, SessaoJuridica sessaoJuridicaSalva) {
		if (sessaoJuridica.getStatusAgendamento() == sessaoJuridicaSalva.getStatusAgendamento()) {
			if (sessaoJuridica.getStatusAgendamento() == StatusAgendamento.CONFIRMADO) {
				if (!sessaoJuridicaSalva.getAgendamento().toString()
						.equals(sessaoJuridica.getAgendamento().toString())) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isAdiado(SessaoJuridica sessaoJuridica, SessaoJuridica sessaoJuridicaSalva) {
		if (sessaoJuridica.getStatusAgendamento() != sessaoJuridicaSalva.getStatusAgendamento()) {
			if (sessaoJuridica.getStatusAgendamento() == StatusAgendamento.ADIADO) {
				return true;
			}
		}
		return false;
	}

	public boolean isCancelado(SessaoJuridica sessaoJuridica, SessaoJuridica sessaoJuridicaSalva) {
		if (sessaoJuridica.getStatusAgendamento() != sessaoJuridicaSalva.getStatusAgendamento()) {
			if (sessaoJuridica.getStatusAgendamento() == StatusAgendamento.CANCELADO) {
				return true;
			}
		}
		return false;
	}

}