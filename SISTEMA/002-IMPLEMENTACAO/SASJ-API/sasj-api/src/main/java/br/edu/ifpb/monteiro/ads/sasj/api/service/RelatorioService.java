package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.TipoAudiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia;
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

	public ResponseEntity<RelatorioQuantidadeTipoAudiencia> gerarRelatorioQtdTipoAudiencia(LocalDateTime de, 
			LocalDateTime ate) {
		conciliacaoRepository.toString();
		
		AudienciaFilter filterData = new AudienciaFilter();
		filterData.setDataAgendamentoDe(de);
		filterData.setDataAgendamentoAte(ate);
		
		List<Audiencia> audiencias = audienciaRepository.filtrarPorData(filterData);
		
		RelatorioQuantidadeTipoAudiencia relatorioQTA = montarRelatorioQTA(audiencias);

		return ResponseEntity.ok().body(relatorioQTA);

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
			if(audiencia.getTipoAudiencia() == TipoAudiencia.PENAL) {
				qtdPenal++;
			} else if(audiencia.getTipoAudiencia() == TipoAudiencia.ACAO_CIVIL) {
				qtdAcaoCivil++;
			} else if(audiencia.getTipoAudiencia() == TipoAudiencia.CUSTODIA) {
				qtdCustodia++;
			} else if(audiencia.getTipoAudiencia() == TipoAudiencia.IMPROBIDADE) {
				qtdImprobidade++;
			} else if(audiencia.getTipoAudiencia() == TipoAudiencia.INSTRUCAO_CRETA) {
				qtdInstrucaoCreta++;
			} else if(audiencia.getTipoAudiencia() == TipoAudiencia.LEILAO) {
				qtdLeilao++;
			} else if(audiencia.getTipoAudiencia() == TipoAudiencia.PJE) {
				qtdPJE++;
			} else if(audiencia.getTipoAudiencia() == TipoAudiencia.TEBAS_IMPROBIDADE) {
				qtdTebasImprobidade++;
			} else if(audiencia.getTipoAudiencia() == TipoAudiencia.VIDEOCONFERENCIA) {
				qtdVideoconferencia++;
			} else {
				qtdOutros++;
			} 
		}
		
		RelatorioQuantidadeTipoAudiencia RQTA = 
				new RelatorioQuantidadeTipoAudiencia(qtdPenal, qtdAcaoCivil, qtdCustodia,
						qtdImprobidade, qtdInstrucaoCreta, qtdLeilao, qtdPJE, qtdTebasImprobidade,
						qtdVideoconferencia, qtdOutros);
		
		return RQTA;
	}

}