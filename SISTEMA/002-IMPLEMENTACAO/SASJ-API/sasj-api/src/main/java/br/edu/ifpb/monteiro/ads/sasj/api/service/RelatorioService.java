package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.model.relatorio.RelatorioQuantidadeTipoAudiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.AudienciaRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.ConciliacaoRepository;

@Service
public class RelatorioService {

	@Autowired
	private AudienciaRepository audienciaRepository;

	@Autowired
	private ConciliacaoRepository conciliacaoRepository;

	public ResponseEntity<RelatorioQuantidadeTipoAudiencia> gerarRelatorioQtdTipoAudiencia(LocalDateTime de, 
			LocalDateTime ate) {
		audienciaRepository.toString();
		conciliacaoRepository.toString();

		RelatorioQuantidadeTipoAudiencia rqta = 
				new RelatorioQuantidadeTipoAudiencia(23, 32, 44, 12, 100, 2, 3, 43, 30, 5);

		return ResponseEntity.ok().body(rqta);

	}

}