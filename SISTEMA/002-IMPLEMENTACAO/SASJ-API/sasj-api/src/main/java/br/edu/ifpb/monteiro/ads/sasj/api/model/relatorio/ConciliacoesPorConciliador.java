package br.edu.ifpb.monteiro.ads.sasj.api.model.relatorio;

public class ConciliacoesPorConciliador {

	private String nomeConciliador;
	private int numeroConciliacoes;

	public ConciliacoesPorConciliador(String nomeConciliador, int numeroConciliacoes) {
		this.nomeConciliador = nomeConciliador;
		this.numeroConciliacoes = numeroConciliacoes;
	}

	public String getNomeConciliador() {
		return nomeConciliador;
	}

	public void setNomeConciliador(String nomeConciliador) {
		this.nomeConciliador = nomeConciliador;
	}

	public int getNumeroConciliacoes() {
		return numeroConciliacoes;
	}

	public void setNumeroConciliacoes(int numeroConciliacoes) {
		this.numeroConciliacoes = numeroConciliacoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeConciliador == null) ? 0 : nomeConciliador.hashCode());
		result = prime * result + numeroConciliacoes;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConciliacoesPorConciliador other = (ConciliacoesPorConciliador) obj;
		if (nomeConciliador == null) {
			if (other.nomeConciliador != null)
				return false;
		} else if (!nomeConciliador.equalsIgnoreCase(other.nomeConciliador))
			return false;
		if (numeroConciliacoes != other.numeroConciliacoes)
			return false;
		return true;
	}

}