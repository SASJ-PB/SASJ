package br.edu.ifpb.monteiro.ads.sasj.api.enums;

public enum TipoFuncionario {

	ADMIN("Admin"),
	PADRAO("Padrão");
	
	private String descricao;
	
	TipoFuncionario(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}