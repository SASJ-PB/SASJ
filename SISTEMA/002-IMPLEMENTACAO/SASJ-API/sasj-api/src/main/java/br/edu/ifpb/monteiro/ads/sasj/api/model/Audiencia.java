package br.edu.ifpb.monteiro.ads.sasj.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.TipoAudiencia;

@Entity
@PrimaryKeyJoinColumn(name = "codigo")
@Table(name = "audiencia")
public class Audiencia extends SessaoJuridica {

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_audiencia")
	private TipoAudiencia tipoAudiencia;

	public TipoAudiencia getTipoAudiencia() {
		return tipoAudiencia;
	}

	public void setTipoAudiencia(TipoAudiencia tipoAudiencia) {
		this.tipoAudiencia = tipoAudiencia;
	}

}