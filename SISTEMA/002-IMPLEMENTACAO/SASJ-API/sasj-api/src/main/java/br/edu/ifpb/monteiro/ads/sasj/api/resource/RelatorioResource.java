package br.edu.ifpb.monteiro.ads.sasj.api.resource;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.monteiro.ads.sasj.api.model.relatorio.RelatorioQuantidadeTipoAudiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.service.RelatorioService;

@RestController
@RequestMapping("/relatorio")
public class RelatorioResource {

	@Autowired
	private RelatorioService relatorioService;

	@GetMapping("/quantidadeTipoAudiencia")
	public ResponseEntity<RelatorioQuantidadeTipoAudiencia> gerarRelatorio(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime de,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime ate) {

		return relatorioService.gerarRelatorioQtdTipoAudiencia(de, ate);

	}

}