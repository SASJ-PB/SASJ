package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.threeten.extra.Interval;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.StatusAgendamento;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Conciliacao;
import br.edu.ifpb.monteiro.ads.sasj.api.model.SessaoJuridica;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.AudienciaRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.ConciliacaoRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.AgendamentoConclituosoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.AudienciaComHorarioJaOcupadoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.ConciliacaoComHorarioJaOcupadoException;

@Service
public class AgendamentoService {

	@Autowired
	private AudienciaRepository audienciaRepository;

	@Autowired
	private ConciliacaoRepository conciliacaoRepository;

	@Autowired
	private EmailService emailService;

	public void validarAgendamento(SessaoJuridica sessaoJuridica) {

		if (sessaoJuridica instanceof Audiencia) {
			validarAgendamentoDeAudiencia(sessaoJuridica);
		} else if (sessaoJuridica instanceof Conciliacao) {
			validarAgendamentoDeConciliacao(sessaoJuridica);
		}

	}

	private void validarAgendamentoDeAudiencia(SessaoJuridica agendamentoPretendido) {
		List<Audiencia> audiencias = audienciaRepository.findAll();

		try {
			for (Audiencia audiencia : audiencias) {
				if (audiencia.getStatusAgendamento() == StatusAgendamento.CONFIRMADO) {
					verificarConflito(audiencia, agendamentoPretendido);
				}
			}
		} catch (AgendamentoConclituosoException ex) {
			throw new AudienciaComHorarioJaOcupadoException();
		}
	}

	private void validarAgendamentoDeConciliacao(SessaoJuridica agendamentoPretendido) {
		List<Conciliacao> conciliacoes = conciliacaoRepository.findAll();

		try {
			for (Conciliacao conciliacao : conciliacoes) {
				if (conciliacao.getStatusAgendamento() == StatusAgendamento.CONFIRMADO) {
					verificarConflito(conciliacao, agendamentoPretendido);
				}
			}
		} catch (AgendamentoConclituosoException ex) {
			throw new ConciliacaoComHorarioJaOcupadoException();
		}
	}

	private void verificarConflito(SessaoJuridica agendamentoCadastrado, SessaoJuridica agendamentoPretendido)
			throws AgendamentoConclituosoException {
		ZoneId fusoHorarioRecifeNordeste = ZoneId.of("America/Recife");

		LocalDateTime ldtCadastrado = agendamentoCadastrado.getAgendamento();
		Integer duracaoCadastrada = agendamentoCadastrado.getDuracaoEstimada();

		LocalDateTime ldtPretendido = agendamentoPretendido.getAgendamento();
		Integer duracaoPretendida = agendamentoPretendido.getDuracaoEstimada();

		Interval intervaloCadastrado = Interval.of(ldtCadastrado.atZone(fusoHorarioRecifeNordeste).toInstant(),
				ldtCadastrado.plusMinutes(duracaoCadastrada).atZone(fusoHorarioRecifeNordeste).toInstant());

		Interval intervaloPretendido = Interval.of(ldtPretendido.atZone(fusoHorarioRecifeNordeste).toInstant(),
				ldtPretendido.plusMinutes(duracaoPretendida).atZone(fusoHorarioRecifeNordeste).toInstant());

		if (intervaloCadastrado.isConnected(intervaloPretendido)) {
			if (agendamentoCadastrado.getCodigo() != agendamentoPretendido.getCodigo()) {
				throw new AgendamentoConclituosoException();
			}
		}

	}

	public void notificarPartesSobreConfirmacao(SessaoJuridica sessaoJuridica) {
		if (possuiPartesInteressadas(sessaoJuridica)) {
			if (sessaoJuridica instanceof Audiencia) {
				emailService.enviarEmailLembreteDeAudienciaConfirmada(sessaoJuridica);
			} else if (sessaoJuridica instanceof Conciliacao) {
				emailService.enviarEmailLembreteDeConciliacaoConfirmada(sessaoJuridica);
			}
		}
	}
	
	public void notificarPartesSobreReagendamento(SessaoJuridica sessaoJuridica) {
		if (possuiPartesInteressadas(sessaoJuridica)) {
			if (sessaoJuridica instanceof Audiencia) {
				emailService.enviarEmailLembreteDeAudienciaReagendada(sessaoJuridica);
			} else if (sessaoJuridica instanceof Conciliacao) {
				emailService.enviarEmailLembreteDeConciliacaoReagendada(sessaoJuridica);
			}
		}
	}
	
	public void notificarPartesSobreAdiamento(SessaoJuridica sessaoJuridica) {
		
	}

	private boolean possuiPartesInteressadas(SessaoJuridica sessaoJuridica) {
		if (sessaoJuridica.getPartesInteressadas() != null) {
			if (!sessaoJuridica.getPartesInteressadas().isEmpty()) {
				return true;
			}
		}
		return false;
	}

}