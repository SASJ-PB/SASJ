package br.edu.ifpb.monteiro.ads.sasj.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TokenVerificacao.class)
public abstract class TokenVerificacao_ {

	public static volatile SingularAttribute<TokenVerificacao, Long> codigo;
	public static volatile SingularAttribute<TokenVerificacao, Usuario> usuario;
	public static volatile SingularAttribute<TokenVerificacao, String> token;

}

