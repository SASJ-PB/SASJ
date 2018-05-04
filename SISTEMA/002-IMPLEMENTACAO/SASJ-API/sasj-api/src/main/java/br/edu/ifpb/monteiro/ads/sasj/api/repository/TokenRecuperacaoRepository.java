package br.edu.ifpb.monteiro.ads.sasj.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.monteiro.ads.sasj.api.model.TokenRecuperacao;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;

public interface TokenRecuperacaoRepository extends JpaRepository<TokenRecuperacao, Long> {

	public TokenRecuperacao findByToken(String token);

	public TokenRecuperacao findByUsuario(Usuario usuario);

}