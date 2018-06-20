package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.StatusAgendamento;
import br.edu.ifpb.monteiro.ads.sasj.api.model.ParteInteressada;
import br.edu.ifpb.monteiro.ads.sasj.api.model.SessaoJuridica;

@Service
public class NotificacaoService {

	@Autowired
	private AgendamentoService agendamentoService;

	public void definirNotificacao(SessaoJuridica sessaoJuridicaSalva, boolean isAudienciaConfirmada,
			boolean isReagendada, boolean isAudienciaAdiada, boolean isAudienciaCancelada,
			boolean possuiNovaParteInteressada) {
		if (isAudienciaConfirmada) {
			notificarPartesSobreConfirmacao(sessaoJuridicaSalva);
		} else if (isReagendada) {
			notificarPartesSobreReagendamento(sessaoJuridicaSalva);
		} else if (isAudienciaAdiada) {
			notificarPartesSobreAdiamento(sessaoJuridicaSalva);
		} else if (isAudienciaCancelada) {
			notificarPartesSobreCancelamento(sessaoJuridicaSalva);
		} else if (possuiNovaParteInteressada) {
			List<ParteInteressada> novasPartesInteressadas = obterNovasPartesInteressadas(sessaoJuridicaSalva);
			notificarNovasParteInteressadas(sessaoJuridicaSalva, novasPartesInteressadas);
		}
	}

	private void notificarNovasParteInteressadas(SessaoJuridica sessaoJuridicaSalva,
			List<ParteInteressada> novasPartesInteressadas) {
		agendamentoService.notificarNovasPartesInteressadasSobreEstadoAtual(sessaoJuridicaSalva,
				novasPartesInteressadas);
	}

	public void notificarPartesSobreConfirmacao(SessaoJuridica sessaoJuridica) {
		agendamentoService.notificarPartesSobreConfirmacao(sessaoJuridica);
	}

	public void notificarPartesSobreReagendamento(SessaoJuridica sessaoJuridica) {
		agendamentoService.notificarPartesSobreReagendamento(sessaoJuridica);
	}

	public void notificarPartesSobreAdiamento(SessaoJuridica sessaoJuridica) {
		agendamentoService.notificarPartesSobreAdiamento(sessaoJuridica);
	}

	public void notificarPartesSobreCancelamento(SessaoJuridica sessaoJuridica) {
		agendamentoService.notificarPartesSobreCancelamento(sessaoJuridica);
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

	public boolean possuiNovaParteInteressada(SessaoJuridica sessaoJuridica) {
		List<ParteInteressada> partesInteressadas = sessaoJuridica.getPartesInteressadas();
		for (ParteInteressada parteInteressada : partesInteressadas) {
			if (parteInteressada.getCodigo() == null) {
				return true;
			}
		}
		return false;
	}

	private List<ParteInteressada> obterNovasPartesInteressadas(SessaoJuridica sessaoJuridica) {
		List<ParteInteressada> partesInteressadas = sessaoJuridica.getPartesInteressadas();
		List<ParteInteressada> novasPartesInteressadas = new ArrayList<>();

		for (ParteInteressada parteInteressada : partesInteressadas) {
			if (parteInteressada.getCodigo() == null) {
				novasPartesInteressadas.add(parteInteressada);
			}
		}
		return novasPartesInteressadas;
	}

}