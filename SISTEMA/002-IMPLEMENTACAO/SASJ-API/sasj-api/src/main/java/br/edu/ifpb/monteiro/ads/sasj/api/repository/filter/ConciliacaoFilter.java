package br.edu.ifpb.monteiro.ads.sasj.api.repository.filter;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.StatusAgendamento;

public class ConciliacaoFilter {

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime dataAgendamentoDe;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime dataAgendamentoAte;

	private StatusAgendamento statusAgendamento;

	private Integer quantidadeOitivasDe;

	private Integer quantidadeOitivasAte;

	private Integer duracaoEstimadaDe;

	private Integer duracaoEstimadaAte;

	private String observacao;

	private String numeroProcesso;

	private String nomeDaParteProcesso;

	private String nomeConciliador;

	public LocalDateTime getDataAgendamentoDe() {
		return dataAgendamentoDe;
	}

	public void setDataAgendamentoDe(LocalDateTime dataAgendamentoDe) {
		this.dataAgendamentoDe = dataAgendamentoDe;
	}

	public LocalDateTime getDataAgendamentoAte() {
		return dataAgendamentoAte;
	}

	public void setDataAgendamentoAte(LocalDateTime dataAgendamentoAte) {
		this.dataAgendamentoAte = dataAgendamentoAte;
	}

	public StatusAgendamento getStatusAgendamento() {
		return statusAgendamento;
	}

	public void setStatusAgendamento(StatusAgendamento statusAgendamento) {
		this.statusAgendamento = statusAgendamento;
	}

	public Integer getQuantidadeOitivasDe() {
		return quantidadeOitivasDe;
	}

	public void setQuantidadeOitivasDe(Integer quantidadeOitivasDe) {
		this.quantidadeOitivasDe = quantidadeOitivasDe;
	}

	public Integer getQuantidadeOitivasAte() {
		return quantidadeOitivasAte;
	}

	public void setQuantidadeOitivasAte(Integer quantidadeOitivasAte) {
		this.quantidadeOitivasAte = quantidadeOitivasAte;
	}

	public Integer getDuracaoEstimadaDe() {
		return duracaoEstimadaDe;
	}

	public void setDuracaoEstimadaDe(Integer duracaoEstimadaDe) {
		this.duracaoEstimadaDe = duracaoEstimadaDe;
	}

	public Integer getDuracaoEstimadaAte() {
		return duracaoEstimadaAte;
	}

	public void setDuracaoEstimadaAte(Integer duracaoEstimadaAte) {
		this.duracaoEstimadaAte = duracaoEstimadaAte;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public String getNomeDaParteProcesso() {
		return nomeDaParteProcesso;
	}

	public void setNomeDaParteProcesso(String nomeDaParteProcesso) {
		this.nomeDaParteProcesso = nomeDaParteProcesso;
	}

	public String getNomeConciliador() {
		return nomeConciliador;
	}

	public void setNomeConciliador(String nomeConciliador) {
		this.nomeConciliador = nomeConciliador;
	}

}