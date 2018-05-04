package br.edu.ifpb.monteiro.ads.sasj.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.UsuarioRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.UsuarioFilter;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.EmailJaCadastradoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.NomeInvalidoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.SenhaAntigaNaoConfereException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.UsuarioInexistenteOuInativoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.util.RegexNomeValidator;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenVerificacaoService tokenVerificacaoService;

	public Usuario atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Usuario usuarioSalvo = buscarUsuarioPeloCodigo(codigo);
		if (usuarioSalvo == null) {
			throw new UsuarioInexistenteOuInativoException();			
		}
		usuarioSalvo.setAtivo(ativo);
		return usuarioRepository.save(usuarioSalvo);
	}

	public Usuario buscarUsuarioPeloCodigo(Long codigo) {
		Usuario usuario = usuarioRepository.findOne(codigo);
		if (usuario == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return usuario;
	}

	public Usuario buscarUsuarioPeloEmail(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		if (usuario == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return usuario;
	}

	public Usuario atualizar(Long codigo, Usuario usuario) {
		Usuario usuarioSalvo = buscarUsuarioPeloCodigo(codigo);	
		
		// VERIFICAR SENHA
		if(!usuario.getSenha().equals(usuarioSalvo.getSenha())) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			usuario.setSenha(encoder.encode(usuario.getSenha()));
		}

		BeanUtils.copyProperties(usuario, usuarioSalvo, "codigo", "permissoes");
		validarAtualizacao(usuario);

		return usuarioRepository.save(usuarioSalvo);
	}
	
	public Usuario remover(Long codigo) {
		Usuario usuario = atualizarPropriedadeAtivo(codigo, false);
		tokenVerificacaoService.criarTokenVerificacao(usuario);
		return usuario;
	}

	private void validarAtualizacao(Usuario usuarioNovaVersao) {
		Usuario usuarioEmailCadastrado = usuarioRepository.findByEmail(usuarioNovaVersao.getEmail());

		if (!RegexNomeValidator.isNomeValido(usuarioNovaVersao.getNome())) {
			throw new NomeInvalidoException();
		}

		else if (usuarioEmailCadastrado != null) {
			if (!usuarioEmailCadastrado.getCodigo().equals(usuarioNovaVersao.getCodigo())) {
				throw new EmailJaCadastradoException();
			}
		}		
	}

	public boolean verificarSenha(Long codigo, String senhaAntiga) {
		Usuario usuario = buscarUsuarioPeloCodigo(codigo);
		
		if(usuario != null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if(encoder.matches(senhaAntiga, usuario.getSenha())) {
				return true;
			}
			else {
				throw new SenhaAntigaNaoConfereException();				
			}
		}
		return false;
	}

	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable) {
		
		return usuarioRepository.filtrar(usuarioFilter,pageable);
	}

}
