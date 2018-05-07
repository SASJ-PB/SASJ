package br.edu.ifpb.monteiro.ads.sasj.api.enums;

public enum TipoUsuario {

	ADMIN("Admin"),
	PADRAO("Padrão");
	
	private String descricao;
	
	TipoUsuario(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}