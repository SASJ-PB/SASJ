package br.edu.ifpb.monteiro.ads.sasj.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TokenRecuperacao.class)
public abstract class TokenRecuperacao_ {

	public static volatile SingularAttribute<TokenRecuperacao, Long> codigo;
	public static volatile SingularAttribute<TokenRecuperacao, Usuario> usuario;
	public static volatile SingularAttribute<TokenRecuperacao, String> token;

}

