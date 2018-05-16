package br.edu.ifpb.monteiro.ads.sasj.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name = "codigo")
@Table(name = "conciliacao")
public class Conciliacao extends SessaoJuridica {

	@NotNull
	@Column(name = "nome_conciliador")
	private String nomeConciliador;

	public String getNomeConciliador() {
		return nomeConciliador;
	}

	public void setNomeConciliador(String nomeConciliador) {
		this.nomeConciliador = nomeConciliador;
	}

}