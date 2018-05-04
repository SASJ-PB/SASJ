package br.edu.ifpb.monteiro.ads.sasj.api.repository.usuario;

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

import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario_;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.UsuarioFilter;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		Predicate[] predicates = criarRestricoes(usuarioFilter,builder, root);
		criteria.where(predicates);

		TypedQuery<Usuario> query = manager.createQuery(criteria);

		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(usuarioFilter));
	}	

	private Predicate[] criarRestricoes(UsuarioFilter usuarioFilter, CriteriaBuilder builder, Root<Usuario> root) {
		List<Predicate> predicates = new ArrayList<>();

		if(usuarioFilter.getNome() != null) {
			predicates.add(builder.like(builder.lower(root.get(Usuario_.nome)),
					"%" + usuarioFilter.getNome() + "%")
					);
		}

		if(usuarioFilter.getDataNascimento() != null) {
			predicates.add(builder.like(root.get(Usuario_.dataNascimento.toString()),
					"%" + usuarioFilter.getDataNascimento())
					);
		}

		if(usuarioFilter.getEmail() != null) {
			predicates.add(builder.like(builder.lower(root.get(Usuario_.email)),
					"%" + usuarioFilter.getEmail() + "%")
					);
		}

		if(usuarioFilter.getTelefone() != null) {
			predicates.add(builder.like(builder.lower(root.get(Usuario_.telefone)),
					"%" + usuarioFilter.getTelefone() + "%")
					);
		}

		if(usuarioFilter.isAtivo() != null) {
			if(usuarioFilter.isAtivo()) {
				predicates.add(builder.isTrue(root.get(Usuario_.ativo)));
			}
			else {
				predicates.add(builder.isFalse(root.get(Usuario_.ativo)));
			}
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long total(UsuarioFilter usuarioFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		Predicate[] predicates = criarRestricoes(usuarioFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<Usuario> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int registrosPorPagina= pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * registrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(registrosPorPagina);
	}

}
