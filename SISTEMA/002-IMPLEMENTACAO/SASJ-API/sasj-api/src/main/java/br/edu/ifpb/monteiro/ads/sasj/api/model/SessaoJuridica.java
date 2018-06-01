package br.edu.ifpb.monteiro.ads.sasj.api.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.StatusAgendamento;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "sessao_juridica")
public abstract class SessaoJuridica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "codigo_processo", nullable = false)
	@NotNull
	private Processo processo;
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime agendamento;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status_agendamento")
	private StatusAgendamento statusAgendamento;
	
	@NotNull
	@Column(name = "quantidade_oitivas")
	private Integer quantidadeOitivas;

	// Duracao em minutos
	@NotNull
	@Column(name = "duracao_estimada")
	private Integer duracaoEstimada;

	private String observacao;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public LocalDateTime getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(LocalDateTime agendamento) {
		this.agendamento = agendamento;
	}
	
	public StatusAgendamento getStatusAgendamento() {
		return statusAgendamento;
	}

	public void setStatusAgendamento(StatusAgendamento statusAgendamento) {
		this.statusAgendamento = statusAgendamento;
	}

	public Integer getQuantidadeOitivas() {
		return quantidadeOitivas;
	}

	public void setQuantidadeOitivas(Integer quantidadeOitivas) {
		this.quantidadeOitivas = quantidadeOitivas;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getDuracaoEstimada() {
		return duracaoEstimada;
	}

	public void setDuracaoEstimada(Integer duracaoEstimada) {
		this.duracaoEstimada = duracaoEstimada;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		SessaoJuridica other = (SessaoJuridica) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}