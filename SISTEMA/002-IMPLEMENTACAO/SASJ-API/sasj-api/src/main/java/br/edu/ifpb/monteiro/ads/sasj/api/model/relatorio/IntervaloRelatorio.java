package br.edu.ifpb.monteiro.ads.sasj.api.model.relatorio;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class IntervaloRelatorio {

	@NotNull
	private LocalDateTime de;
	
	@NotNull
	private LocalDateTime ate;
	
	public LocalDateTime getDe() {
		return de;
	}
	public void setDe(LocalDateTime de) {
		this.de = de;
	}
	public LocalDateTime getAte() {
		return ate;
	}
	public void setAte(LocalDateTime ate) {
		this.ate = ate;
	}
	
}