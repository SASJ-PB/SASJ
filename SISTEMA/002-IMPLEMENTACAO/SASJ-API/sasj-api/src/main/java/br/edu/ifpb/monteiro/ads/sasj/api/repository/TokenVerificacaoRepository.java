package br.edu.ifpb.monteiro.ads.sasj.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.monteiro.ads.sasj.api.model.TokenVerificacao;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;

public interface TokenVerificacaoRepository extends JpaRepository<TokenVerificacao, Long> {
	
	public TokenVerificacao findByToken(String token);
	
	public TokenVerificacao findByUsuario(Usuario usuario);

}