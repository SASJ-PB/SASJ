package br.edu.ifpb.monteiro.ads.sasj.api.repository.filter;

public class TerapiaFilter {
	
	private String descricao;

	private Boolean ativa;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}
	
	
}
