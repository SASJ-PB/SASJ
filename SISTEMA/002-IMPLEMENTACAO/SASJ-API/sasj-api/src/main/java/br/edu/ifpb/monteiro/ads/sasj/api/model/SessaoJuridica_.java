package br.edu.ifpb.monteiro.ads.sasj.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SessaoJuridica.class)
public abstract class SessaoJuridica_ {

	public static volatile SingularAttribute<SessaoJuridica, Long> codigo;
	public static volatile SingularAttribute<SessaoJuridica, String> observacao;
	public static volatile SingularAttribute<SessaoJuridica, Integer> duracaoEstimada;
	public static volatile SingularAttribute<SessaoJuridica, Processo> processo;
	public static volatile SingularAttribute<SessaoJuridica, Integer> quantidadeOitivas;

}
