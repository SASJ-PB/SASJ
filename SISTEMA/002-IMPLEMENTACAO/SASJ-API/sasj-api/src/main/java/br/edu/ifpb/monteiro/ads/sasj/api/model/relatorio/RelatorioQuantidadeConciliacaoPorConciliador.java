package br.edu.ifpb.monteiro.ads.sasj.api.model.relatorio;

import java.util.List;

public class RelatorioQuantidadeConciliacaoPorConciliador {

	private List<ConciliacoesPorConciliador> conciliacoesPorConciliador;

	public RelatorioQuantidadeConciliacaoPorConciliador(List<ConciliacoesPorConciliador> conciliacoesPorConciliador) {
		this.conciliacoesPorConciliador = conciliacoesPorConciliador;
	}

	public List<ConciliacoesPorConciliador> getConciliacoesPorConciliador() {
		return conciliacoesPorConciliador;
	}

	public void setConciliacoesPorConciliador(List<ConciliacoesPorConciliador> conciliacoesPorConciliador) {
		this.conciliacoesPorConciliador = conciliacoesPorConciliador;
	}
	
}