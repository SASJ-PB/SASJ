package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.TipoAudiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.relatorio.RelatorioQuantidadeOitivaTipoAudiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.relatorio.RelatorioQuantidadeTipoAudiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.AudienciaRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.ConciliacaoRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.AudienciaFilter;

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

	public RelatorioQuantidadeOitivaTipoAudiencia gerarRelatorioQtdOitivaTipoAudiencia(LocalDateTime de, LocalDateTime ate) {

		AudienciaFilter filterData = new AudienciaFilter();
		filterData.setDataAgendamentoDe(de);
		filterData.setDataAgendamentoAte(ate);

		List<Audiencia> audiencias = audienciaRepository.filtrarPorData(filterData);

		RelatorioQuantidadeOitivaTipoAudiencia relatorioQOTA = montarRelatorioQOTA(audiencias);

		return relatorioQOTA;

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

	private RelatorioQuantidadeOitivaTipoAudiencia montarRelatorioQOTA(List<Audiencia> audiencias) {

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

		RelatorioQuantidadeOitivaTipoAudiencia RQOTA = new RelatorioQuantidadeOitivaTipoAudiencia(qtdOitivaPenal,
				qtdOitivaAcaoCivil, qtdOitivaCustodia, qtdOitivaImprobidade, qtdOitivaInstrucaoCreta, qtdOitivaLeilao,
				qtdOitivaPJE, qtdOitivaTebasImprobidade, qtdOitivaVideoconferencia, qtdOitivaOutros);

		return RQOTA;
	}

}