package br.edu.ifpb.monteiro.ads.sasj.api.enums;

public enum TipoAudiencia {

	PENAL("Penal"),
	ACAO_CIVIL("Ação Civil"),
	IMPROBIDADE("Improbidade"),
	INSTRUCAO_CRETA("Instrução Creta"),
	LEILAO("Leilão"),
	PJE("PJE"),
	TEBAS_IMPROBIDADE("Tebas Impobidade"),
	VIDEOCONFERENCIA("Videoconferência"),
	OUTROS("Outros");
	
	private String descricao;
	
	TipoAudiencia(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}