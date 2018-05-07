package br.edu.ifpb.monteiro.ads.sasj.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.TipoUsuario;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByEmail(String email);

	public Usuario findByMatricula(String matricula);
	
	public List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);

}