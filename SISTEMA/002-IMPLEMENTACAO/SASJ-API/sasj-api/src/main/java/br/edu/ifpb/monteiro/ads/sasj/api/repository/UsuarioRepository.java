package br.edu.ifpb.monteiro.ads.sasj.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.usuario.UsuarioRepositoryQuery;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {

	public Usuario findByEmail(String email);

	public Usuario findByMatricula(String matricula);

}