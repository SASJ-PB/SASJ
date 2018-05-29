package br.edu.ifpb.monteiro.ads.sasj.api.enums;

public enum StatusAgendamento {

	CONFIRMADO("Confirmado"),
	ADIADO("Adiado"),
	CANCELADO("Cancelado");
	
	private String descricao;
	
	StatusAgendamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}