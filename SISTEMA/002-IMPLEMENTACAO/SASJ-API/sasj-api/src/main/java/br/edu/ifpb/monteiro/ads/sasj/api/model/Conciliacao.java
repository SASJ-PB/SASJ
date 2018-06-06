package br.edu.ifpb.monteiro.ads.sasj.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@PrimaryKeyJoinColumn(name = "codigo")
@Table(name = "conciliacao")
public class Conciliacao extends SessaoJuridica {

	@NotBlank(message = "O nome do conciliador não pode ser vazio")
	@Column(name = "nome_conciliador")
	private String nomeConciliador;

	public String getNomeConciliador() {
		return nomeConciliador;
	}

	public void setNomeConciliador(String nomeConciliador) {
		this.nomeConciliador = nomeConciliador;
	}

}