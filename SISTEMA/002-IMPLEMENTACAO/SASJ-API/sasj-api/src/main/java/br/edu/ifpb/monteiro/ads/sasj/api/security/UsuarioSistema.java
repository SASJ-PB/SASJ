package br.edu.ifpb.monteiro.ads.sasj.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private String tipo;

	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
		this.tipo = getTypeOf(usuario);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public String getTipo() {
		return tipo;
	}

	private String getTypeOf(Usuario usuario) {
		// if (usuario instanceof Cliente) {
		// return "cliente";
		// }
		// else if (usuario instanceof Funcionario) {
		// return "funcionario";
		// }
		// else if (usuario instanceof Profissional) {
		// return "profissional";
		// }
		return "usuario";
	}

}
