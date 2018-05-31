package br.edu.ifpb.monteiro.ads.sasj.api.repository.conciliacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Conciliacao;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.ConciliacaoFilter;

public interface ConciliacaoRepositoryQuery {

	public Page<Conciliacao> filtrar(ConciliacaoFilter conciliacaoFilter, Pageable pageable);

}