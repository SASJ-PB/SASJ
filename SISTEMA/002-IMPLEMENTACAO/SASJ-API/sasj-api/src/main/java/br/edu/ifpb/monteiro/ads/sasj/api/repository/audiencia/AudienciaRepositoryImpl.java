package br.edu.ifpb.monteiro.ads.sasj.api.repository.audiencia;

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

import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia_;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.AudienciaFilter;

public class AudienciaRepositoryImpl implements AudienciaRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Audiencia> filtrar(AudienciaFilter audienciaFilter, Pageable pageable) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Audiencia> criteria = builder.createQuery(Audiencia.class);

		Root<Audiencia> root = criteria.from(Audiencia.class);

		// Criar restricoes
		Predicate[] predicates = criarRestricoes(audienciaFilter, builder, root);

		criteria.where(predicates);

		TypedQuery<Audiencia> query = entityManager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(audienciaFilter));
	}

	@Override
	public List<Audiencia> filtrarPorData(AudienciaFilter audienciaFilterData) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Audiencia> criteria = builder.createQuery(Audiencia.class);

		Root<Audiencia> root = criteria.from(Audiencia.class);

		// Criar restricoes
		Predicate[] predicates = criarRestricoes(audienciaFilterData, builder, root);

		criteria.where(predicates);

		TypedQuery<Audiencia> query = entityManager.createQuery(criteria);

		return query.getResultList();
	}

	private Predicate[] criarRestricoes(AudienciaFilter audienciaFilter, CriteriaBuilder builder,
			Root<Audiencia> root) {

		List<Predicate> predicates = new ArrayList<>();

		// Data do agendamento
		if (audienciaFilter.getDataAgendamentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Audiencia_.agendamento),
					audienciaFilter.getDataAgendamentoDe()));
		}

		if (audienciaFilter.getDataAgendamentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Audiencia_.agendamento),
					audienciaFilter.getDataAgendamentoAte()));
		}

		// Quantidade de oitivas
		if (audienciaFilter.getQuantidadeOitivasDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Audiencia_.quantidadeOitivas),
					audienciaFilter.getQuantidadeOitivasDe()));
		}

		if (audienciaFilter.getQuantidadeOitivasAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Audiencia_.quantidadeOitivas),
					audienciaFilter.getQuantidadeOitivasAte()));
		}

		// Duração estimada da sessão
		if (audienciaFilter.getDuracaoEstimadaDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Audiencia_.duracaoEstimada),
					audienciaFilter.getDuracaoEstimadaDe()));
		}

		if (audienciaFilter.getDuracaoEstimadaAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Audiencia_.duracaoEstimada),
					audienciaFilter.getDuracaoEstimadaAte()));
		}

		// Status do agendamento
		if (audienciaFilter.getStatusAgendamento() != null) {
			predicates
					.add(builder.equal(root.get(Audiencia_.statusAgendamento), audienciaFilter.getStatusAgendamento()));
		}

		// Tipo audiência
		if (audienciaFilter.getTipoAudiencia() != null) {
			predicates.add(builder.equal(root.get(Audiencia_.tipoAudiencia), audienciaFilter.getTipoAudiencia()));
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

	private Long total(AudienciaFilter audienciaFilter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

		Root<Audiencia> root = criteria.from(Audiencia.class);

		Predicate[] predicates = criarRestricoes(audienciaFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));

		return entityManager.createQuery(criteria).getSingleResult();
	}

}