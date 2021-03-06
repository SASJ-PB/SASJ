package br.edu.ifpb.monteiro.ads.sasj.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Conciliacao;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Processo;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.conciliacao.ConciliacaoRepositoryQuery;

public interface ConciliacaoRepository extends JpaRepository<Conciliacao, Long>, ConciliacaoRepositoryQuery {

	public Conciliacao findByNomeConciliador(String nomeConciliador);

	public List<Conciliacao> findByProcesso(Processo processo);

}