package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.TipoAudiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Conciliacao;
import br.edu.ifpb.monteiro.ads.sasj.api.model.relatorio.ConciliacoesPorConciliador;
import br.edu.ifpb.monteiro.ads.sasj.api.model.relatorio.RelatorioQuantidadeConciliacaoPorConciliador;
import br.edu.ifpb.monteiro.ads.sasj.api.model.relatorio.RelatorioQuantidadeTipoAudiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.AudienciaRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.ConciliacaoRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.AudienciaFilter;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.ConciliacaoFilter;

@Service
public class RelatorioService {

	@Autowired
	private AudienciaRepository audienciaRepository;

	@Autowired
	private ConciliacaoRepository conciliacaoRepository;

	public RelatorioQuantidadeTipoAudiencia gerarRelatorioQtdTipoAudiencia(LocalDateTime de, LocalDateTime ate) {
		conciliacaoRepository.toString();

		AudienciaFilter filterData = new AudienciaFilter();
		filterData.setDataAgendamentoDe(de);
		filterData.setDataAgendamentoAte(ate);

		List<Audiencia> audiencias = audienciaRepository.filtrarPorData(filterData);

		RelatorioQuantidadeTipoAudiencia relatorioQTA = montarRelatorioQTA(audiencias);

		return relatorioQTA;

	}

	public RelatorioQuantidadeTipoAudiencia gerarRelatorioQtdOitivaTipoAudiencia(LocalDateTime de, LocalDateTime ate) {

		AudienciaFilter filterData = new AudienciaFilter();
		filterData.setDataAgendamentoDe(de);
		filterData.setDataAgendamentoAte(ate);

		List<Audiencia> audiencias = audienciaRepository.filtrarPorData(filterData);

		RelatorioQuantidadeTipoAudiencia relatorioQOTA = montarRelatorioQOTA(audiencias);

		return relatorioQOTA;

	}

	public RelatorioQuantidadeTipoAudiencia gerarRelatorioQtdHoraTipoAudiencia(LocalDateTime de, LocalDateTime ate) {

		AudienciaFilter filterData = new AudienciaFilter();
		filterData.setDataAgendamentoDe(de);
		filterData.setDataAgendamentoAte(ate);

		List<Audiencia> audiencias = audienciaRepository.filtrarPorData(filterData);

		RelatorioQuantidadeTipoAudiencia relatorioQHTA = montarRelatorioQHTA(audiencias);

		return relatorioQHTA;

	}

	public RelatorioQuantidadeConciliacaoPorConciliador gerarRelatorioQuantidadeConciliacoesPorConciliador(
			LocalDateTime de, LocalDateTime ate) {

		ConciliacaoFilter filterData = new ConciliacaoFilter();
		filterData.setDataAgendamentoDe(de);
		filterData.setDataAgendamentoAte(ate);

		List<Conciliacao> conciliacoes = conciliacaoRepository.filtrarPorData(filterData);

		RelatorioQuantidadeConciliacaoPorConciliador relatorioQCC = montarRelatorioQCC(conciliacoes);

		return relatorioQCC;

	}

	private RelatorioQuantidadeTipoAudiencia montarRelatorioQTA(List<Audiencia> audiencias) {

		int qtdPenal = 0;
		int qtdAcaoCivil = 0;
		int qtdCustodia = 0;
		int qtdImprobidade = 0;
		int qtdInstrucaoCreta = 0;
		int qtdLeilao = 0;
		int qtdPJE = 0;
		int qtdTebasImprobidade = 0;
		int qtdVideoconferencia = 0;
		int qtdOutros = 0;

		for (Audiencia audiencia : audiencias) {
			if (audiencia.getTipoAudiencia() == TipoAudiencia.PENAL) {
				qtdPenal++;
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.ACAO_CIVIL) {
				qtdAcaoCivil++;
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.CUSTODIA) {
				qtdCustodia++;
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.IMPROBIDADE) {
				qtdImprobidade++;
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.INSTRUCAO_CRETA) {
				qtdInstrucaoCreta++;
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.LEILAO) {
				qtdLeilao++;
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.PJE) {
				qtdPJE++;
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.TEBAS_IMPROBIDADE) {
				qtdTebasImprobidade++;
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.VIDEOCONFERENCIA) {
				qtdVideoconferencia++;
			} else {
				qtdOutros++;
			}
		}

		RelatorioQuantidadeTipoAudiencia RQTA = new RelatorioQuantidadeTipoAudiencia(qtdPenal, qtdAcaoCivil,
				qtdCustodia, qtdImprobidade, qtdInstrucaoCreta, qtdLeilao, qtdPJE, qtdTebasImprobidade,
				qtdVideoconferencia, qtdOutros);

		return RQTA;
	}

	private RelatorioQuantidadeTipoAudiencia montarRelatorioQOTA(List<Audiencia> audiencias) {

		int qtdOitivaPenal = 0;
		int qtdOitivaAcaoCivil = 0;
		int qtdOitivaCustodia = 0;
		int qtdOitivaImprobidade = 0;
		int qtdOitivaInstrucaoCreta = 0;
		int qtdOitivaLeilao = 0;
		int qtdOitivaPJE = 0;
		int qtdOitivaTebasImprobidade = 0;
		int qtdOitivaVideoconferencia = 0;
		int qtdOitivaOutros = 0;

		for (Audiencia audiencia : audiencias) {
			if (audiencia.getTipoAudiencia() == TipoAudiencia.PENAL) {
				qtdOitivaPenal += audiencia.getQuantidadeOitivas();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.ACAO_CIVIL) {
				qtdOitivaAcaoCivil += audiencia.getQuantidadeOitivas();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.CUSTODIA) {
				qtdOitivaCustodia += audiencia.getQuantidadeOitivas();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.IMPROBIDADE) {
				qtdOitivaImprobidade += audiencia.getQuantidadeOitivas();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.INSTRUCAO_CRETA) {
				qtdOitivaInstrucaoCreta += audiencia.getQuantidadeOitivas();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.LEILAO) {
				qtdOitivaLeilao += audiencia.getQuantidadeOitivas();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.PJE) {
				qtdOitivaPJE += audiencia.getQuantidadeOitivas();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.TEBAS_IMPROBIDADE) {
				qtdOitivaTebasImprobidade += audiencia.getQuantidadeOitivas();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.VIDEOCONFERENCIA) {
				qtdOitivaVideoconferencia += audiencia.getQuantidadeOitivas();
			} else {
				qtdOitivaOutros += audiencia.getQuantidadeOitivas();
			}
		}

		RelatorioQuantidadeTipoAudiencia RQOTA = new RelatorioQuantidadeTipoAudiencia(qtdOitivaPenal,
				qtdOitivaAcaoCivil, qtdOitivaCustodia, qtdOitivaImprobidade, qtdOitivaInstrucaoCreta, qtdOitivaLeilao,
				qtdOitivaPJE, qtdOitivaTebasImprobidade, qtdOitivaVideoconferencia, qtdOitivaOutros);

		return RQOTA;
	}

	private RelatorioQuantidadeTipoAudiencia montarRelatorioQHTA(List<Audiencia> audiencias) {

		int qtdMinutoPenal = 0;
		int qtdMinutoAcaoCivil = 0;
		int qtdMinutoCustodia = 0;
		int qtdMinutoImprobidade = 0;
		int qtdMinutoInstrucaoCreta = 0;
		int qtdMinutoLeilao = 0;
		int qtdMinutoPJE = 0;
		int qtdMinutoTebasImprobidade = 0;
		int qtdMinutoVideoconferencia = 0;
		int qtdMinutoOutros = 0;

		for (Audiencia audiencia : audiencias) {
			if (audiencia.getTipoAudiencia() == TipoAudiencia.PENAL) {
				qtdMinutoPenal += audiencia.getDuracaoEstimada();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.ACAO_CIVIL) {
				qtdMinutoAcaoCivil += audiencia.getDuracaoEstimada();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.CUSTODIA) {
				qtdMinutoCustodia += audiencia.getDuracaoEstimada();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.IMPROBIDADE) {
				qtdMinutoImprobidade += audiencia.getDuracaoEstimada();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.INSTRUCAO_CRETA) {
				qtdMinutoInstrucaoCreta += audiencia.getDuracaoEstimada();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.LEILAO) {
				qtdMinutoLeilao += audiencia.getDuracaoEstimada();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.PJE) {
				qtdMinutoPJE += audiencia.getDuracaoEstimada();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.TEBAS_IMPROBIDADE) {
				qtdMinutoTebasImprobidade += audiencia.getDuracaoEstimada();
			} else if (audiencia.getTipoAudiencia() == TipoAudiencia.VIDEOCONFERENCIA) {
				qtdMinutoVideoconferencia += audiencia.getDuracaoEstimada();
			} else {
				qtdMinutoOutros += audiencia.getDuracaoEstimada();
			}
		}

		RelatorioQuantidadeTipoAudiencia RQHTA = new RelatorioQuantidadeTipoAudiencia(qtdMinutoPenal,
				qtdMinutoAcaoCivil, qtdMinutoCustodia, qtdMinutoImprobidade, qtdMinutoInstrucaoCreta, qtdMinutoLeilao,
				qtdMinutoPJE, qtdMinutoTebasImprobidade, qtdMinutoVideoconferencia, qtdMinutoOutros);

		return RQHTA;
	}

	private RelatorioQuantidadeConciliacaoPorConciliador montarRelatorioQCC(List<Conciliacao> conciliacoes) {

		List<String> nomeConciliadores = new ArrayList<>();

		for (Conciliacao conciliacao : conciliacoes) {
			if (!nomeConciliadores.contains(conciliacao.getNomeConciliador().toUpperCase())) {
				nomeConciliadores.add(conciliacao.getNomeConciliador().toUpperCase());
			}
		}

		int[] numeroConciliacoes = new int[nomeConciliadores.size()];

		for (int i = 0; i < numeroConciliacoes.length; i++) {
			int numeroDeVezes = 0;
			for (Conciliacao conciliacao : conciliacoes) {
				if (conciliacao.getNomeConciliador().toUpperCase().equals(nomeConciliadores.get(i))) {
					numeroDeVezes++;
				}
			}
			numeroConciliacoes[i] = numeroDeVezes;
		}

		List<ConciliacoesPorConciliador> relacaoConciliacaoConciliador = new ArrayList<>();

		for (int i = 0; i < numeroConciliacoes.length; i++) {
			ConciliacoesPorConciliador conciliacoesPorConciliador = new ConciliacoesPorConciliador(
					nomeConciliadores.get(i), numeroConciliacoes[i]);
			relacaoConciliacaoConciliador.add(conciliacoesPorConciliador);
		}

		RelatorioQuantidadeConciliacaoPorConciliador RQHTA = new RelatorioQuantidadeConciliacaoPorConciliador(
				relacaoConciliacaoConciliador);

		return RQHTA;
	}

}