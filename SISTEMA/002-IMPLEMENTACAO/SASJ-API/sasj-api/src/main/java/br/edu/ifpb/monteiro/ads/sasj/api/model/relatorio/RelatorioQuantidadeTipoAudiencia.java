package br.edu.ifpb.monteiro.ads.sasj.api.model.relatorio;

import java.time.LocalDateTime;

public class RelatorioQuantidadeTipoAudiencia {

	private LocalDateTime de;
	private LocalDateTime ate;
	private int qtdPenal;
	private int qtdAcaoCivil;
	private int qtdCustodia;
	private int qtdImprobidade;
	private int qtdInstrucaoCreta;
	private int qtdLeilao;
	private int qtdPJE;
	private int qtdTebasImprobidade;
	private int qtdVideoconferencia;
	private int qtdOutros;

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

	public int getQtdPenal() {
		return qtdPenal;
	}

	public void setQtdPenal(int qtdPenal) {
		this.qtdPenal = qtdPenal;
	}

	public int getQtdAcaoCivil() {
		return qtdAcaoCivil;
	}

	public void setQtdAcaoCivil(int qtdAcaoCivil) {
		this.qtdAcaoCivil = qtdAcaoCivil;
	}

	public int getQtdCustodia() {
		return qtdCustodia;
	}

	public void setQtdCustodia(int qtdCustodia) {
		this.qtdCustodia = qtdCustodia;
	}

	public int getQtdImprobidade() {
		return qtdImprobidade;
	}

	public void setQtdImprobidade(int qtdImprobidade) {
		this.qtdImprobidade = qtdImprobidade;
	}

	public int getQtdInstrucaoCreta() {
		return qtdInstrucaoCreta;
	}

	public void setQtdInstrucaoCreta(int qtdInstrucaoCreta) {
		this.qtdInstrucaoCreta = qtdInstrucaoCreta;
	}

	public int getQtdLeilao() {
		return qtdLeilao;
	}

	public void setQtdLeilao(int qtdLeilao) {
		this.qtdLeilao = qtdLeilao;
	}

	public int getQtdPJE() {
		return qtdPJE;
	}

	public void setQtdPJE(int qtdPJE) {
		this.qtdPJE = qtdPJE;
	}

	public int getQtdTebasImprobidade() {
		return qtdTebasImprobidade;
	}

	public void setQtdTebasImprobidade(int qtdTebasImprobidade) {
		this.qtdTebasImprobidade = qtdTebasImprobidade;
	}

	public int getQtdVideoconferencia() {
		return qtdVideoconferencia;
	}

	public void setQtdVideoconferencia(int qtdVideoconferencia) {
		this.qtdVideoconferencia = qtdVideoconferencia;
	}

	public int getQtdOutros() {
		return qtdOutros;
	}

	public void setQtdOutros(int qtdOutros) {
		this.qtdOutros = qtdOutros;
	}

}