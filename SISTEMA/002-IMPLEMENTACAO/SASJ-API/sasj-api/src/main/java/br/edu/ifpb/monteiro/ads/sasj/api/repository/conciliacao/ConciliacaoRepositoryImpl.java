package br.edu.ifpb.monteiro.ads.sasj.api.repository.conciliacao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Conciliacao;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Conciliacao_;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.AudienciaFilter;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.ConciliacaoFilter;

public class ConciliacaoRepositoryImpl implements ConciliacaoRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Conciliacao> filtrar(ConciliacaoFilter conciliacaoFilter, Pageable pageable) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Conciliacao> criteria = builder.createQuery(Conciliacao.class);

		Root<Conciliacao> root = criteria.from(Conciliacao.class);

		// Criar restricoes
		Predicate[] predicates = criarRestricoes(conciliacaoFilter, builder, root);

		criteria.where(predicates);

		TypedQuery<Conciliacao> query = entityManager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(conciliacaoFilter));
	}

	@Override
	public List<Conciliacao> filtrarPorData(ConciliacaoFilter conciliacaoFilterData) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Conciliacao> criteria = builder.createQuery(Conciliacao.class);

		Root<Conciliacao> root = criteria.from(Conciliacao.class);

		// Criar restricoes
		Predicate[] predicates = criarRestricoes(conciliacaoFilterData, builder, root);

		criteria.where(predicates);

		TypedQuery<Conciliacao> query = entityManager.createQuery(criteria);

		return query.getResultList();
	}
	
	private Predicate[] criarRestricoes(ConciliacaoFilter conciliacaoFilter, CriteriaBuilder builder,
			Root<Conciliacao> root) {

		List<Predicate> predicates = new ArrayList<>();

		// Data do agendamento
		if (conciliacaoFilter.getDataAgendamentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Conciliacao_.agendamento),
					conciliacaoFilter.getDataAgendamentoDe()));
		}

		if (conciliacaoFilter.getDataAgendamentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Conciliacao_.agendamento),
					conciliacaoFilter.getDataAgendamentoAte()));
		}

		// Quantidade de oitivas
		if (conciliacaoFilter.getQuantidadeOitivasDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Conciliacao_.quantidadeOitivas),
					conciliacaoFilter.getQuantidadeOitivasDe()));
		}

		if (conciliacaoFilter.getQuantidadeOitivasAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Conciliacao_.quantidadeOitivas),
					conciliacaoFilter.getQuantidadeOitivasAte()));
		}

		// Duração estimada da sessão
		if (conciliacaoFilter.getDuracaoEstimadaDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Conciliacao_.duracaoEstimada),
					conciliacaoFilter.getDuracaoEstimadaDe()));
		}

		if (conciliacaoFilter.getDuracaoEstimadaAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Conciliacao_.duracaoEstimada),
					conciliacaoFilter.getDuracaoEstimadaAte()));
		}

		// Status do agendamento
		if (conciliacaoFilter.getStatusAgendamento() != null) {
			predicates.add(
					builder.equal(root.get(Conciliacao_.statusAgendamento), conciliacaoFilter.getStatusAgendamento()));
		}

		// Tipo audiência
		if (!StringUtils.isEmpty(conciliacaoFilter.getNomeConciliador())) {
			predicates.add(builder.like(builder.lower(root.get(Conciliacao_.nomeConciliador)),
					"%" + conciliacaoFilter.getNomeConciliador().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPragina = paginaAtual * totalRegistroPorPagina;

		query.setFirstResult(primeiroRegistroDaPragina);
		query.setMaxResults(totalRegistroPorPagina);
	}

	private Long total(ConciliacaoFilter conciliacaoFilter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

		Root<Conciliacao> root = criteria.from(Conciliacao.class);

		Predicate[] predicates = criarRestricoes(conciliacaoFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));

		return entityManager.createQuery(criteria).getSingleResult();
	}

}