package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.threeten.extra.Interval;

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

	public void validarAgendamento(SessaoJuridica sessaoJuridica) {
		LocalDateTime agendamentoPretendido = sessaoJuridica.getAgendamento();

		validarHorarioConformeFuncionamento(agendamentoPretendido, sessaoJuridica.getDuracaoEstimada());

		if (sessaoJuridica instanceof Audiencia) {
			validarAgendamentoDeAudiencia(sessaoJuridica);
		} else if (sessaoJuridica instanceof Conciliacao) {
			validarAgendamentoDeConciliacao(sessaoJuridica);
		}
	}

	private void validarHorarioConformeFuncionamento(LocalDateTime agendamentoPretendido, Integer duracaoEstimada) {
		// TODO: impedir que sessoes ocorram antes das 9h e impedir que ocorram depois
		// das 18h
	}

	private void validarAgendamentoDeAudiencia(SessaoJuridica agendamentoPretendido) {
		List<Audiencia> audiencias = audienciaRepository.findAll();

		try {
			for (Audiencia audiencia : audiencias) {
				verificarConflito(audiencia, agendamentoPretendido);
			}
		} catch (AgendamentoConclituosoException ex) {
			throw new AudienciaComHorarioJaOcupadoException();
		}
	}

	private void validarAgendamentoDeConciliacao(SessaoJuridica agendamentoPretendido) {
		List<Conciliacao> conciliacoes = conciliacaoRepository.findAll();

		try {
			for (Conciliacao conciliacao : conciliacoes) {
				verificarConflito(conciliacao, agendamentoPretendido);
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

		if (intervaloCadastrado.encloses(intervaloPretendido)) {
			throw new AgendamentoConclituosoException();
		}

	}

}