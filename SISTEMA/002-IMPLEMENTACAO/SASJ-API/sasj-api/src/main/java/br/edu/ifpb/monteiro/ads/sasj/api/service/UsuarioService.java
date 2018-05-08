package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.TipoUsuario;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Permissao;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.UsuarioRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.EmailJaCadastradoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.MatriculaInvalidaException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.MatriculaJaCadastradaException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.NomeInvalidoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.UsuarioInexistenteOuInativoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.UsuarioJaInativoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.util.RegexMatriculaValidator;
import br.edu.ifpb.monteiro.ads.sasj.api.service.util.RegexNomeValidator;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private TokenVerificacaoService tokenVerificacaoService;

	@Autowired
	private TokenRecuperacaoService tokenRecuperacaoService;

	public Usuario criar(Usuario usuario) {
		validarUsuario(usuario);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaTemporaria = String.valueOf(System.currentTimeMillis());

		usuario.setSenha(encoder.encode(senhaTemporaria));
		usuario.setEmail(usuario.getEmail().toLowerCase());
		usuario.setAtivo(true);
		usuario.setMatricula(usuario.getMatricula().toUpperCase());
		usuario.setCargo(usuario.getCargo().toUpperCase());
		usuario.setEmail(usuario.getEmail().toLowerCase());

		adicionarPermissoes(usuario);

		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		// GERANDO TOKEN DE RECUPERACAO DE SENHA
		String tokenParaEmailDeRedefinicao = tokenRecuperacaoService.criarTokenRecuperacao(usuarioSalvo);

		// ENVIANDO EMAIL DE REDEFINIÇÃO
		try {
			emailService.enviarEmailDeRedefinicaoDeSenhaUsuario(usuarioSalvo, tokenParaEmailDeRedefinicao);
		} catch (Exception e) {
			System.out.println("ERRO DE ENVIO: " + e.getMessage());
		}

		return usuarioSalvo;
	}

	public Usuario atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Usuario usuarioSalvo = buscarUsuarioPeloCodigo(codigo);
		if (usuarioSalvo == null) {
			throw new UsuarioInexistenteOuInativoException();
		}
		if(!usuarioSalvo.isAtivo() && !ativo) {
			throw new UsuarioJaInativoException();
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

	public List<Usuario> listar(Usuario usuario) {
		return usuarioRepository.findAll();
	}

	public Usuario atualizar(Long codigo, Usuario usuario) {
		Usuario usuarioSalvo = buscarUsuarioPeloCodigo(codigo);

		BeanUtils.copyProperties(usuario, usuarioSalvo, "codigo", "ativo", "permissoes");
		adicionarPermissoes(usuarioSalvo);

		validarAtualizacao(usuarioSalvo);

		return usuarioRepository.save(usuarioSalvo);
	}

	public Usuario remover(Long codigo) {
		Usuario usuario = atualizarPropriedadeAtivo(codigo, false);
		tokenVerificacaoService.criarTokenVerificacao(usuario);
		return usuario;
	}

	private void validarAtualizacao(Usuario usuarioNovaVersao) {
		Usuario usuarioEmailCadastrado = usuarioRepository.findByEmail(usuarioNovaVersao.getEmail());
		Usuario usuarioMatriculaCadastrada = usuarioRepository.findByMatricula(usuarioNovaVersao.getMatricula());

		if (!RegexNomeValidator.isNomeValido(usuarioNovaVersao.getNome())) {
			throw new NomeInvalidoException();
		}

		if (usuarioEmailCadastrado != null) {
			if (!usuarioEmailCadastrado.getCodigo().equals(usuarioNovaVersao.getCodigo())) {
				throw new EmailJaCadastradoException();
			}
		}

		if (usuarioMatriculaCadastrada != null) {
			if (!usuarioMatriculaCadastrada.getCodigo().equals(usuarioNovaVersao.getCodigo())) {
				throw new MatriculaJaCadastradaException();
			}
		}
		if (!RegexMatriculaValidator.isMatriculaValida(usuarioNovaVersao.getMatricula())) {
			throw new MatriculaInvalidaException();
		}
	}

	public boolean verificarSenha(Long codigo, String senhaAntiga) {
		Usuario usuario = buscarUsuarioPeloCodigo(codigo);

		if (usuario != null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (encoder.matches(senhaAntiga, usuario.getSenha())) {
				return true;
			}
		}
		return false;
	}

	private void validarUsuario(Usuario usuario) {
		Usuario usuarioEmailCadastrado = usuarioRepository.findByEmail(usuario.getEmail());
		Usuario usuarioMatriculaCadastrada = usuarioRepository.findByMatricula(usuario.getMatricula());

		if (usuarioEmailCadastrado != null) {
			throw new EmailJaCadastradoException();
		}
		if (usuarioMatriculaCadastrada != null) {
			throw new MatriculaJaCadastradaException();
		}
		if (!RegexNomeValidator.isNomeValido(usuario.getNome())) {
			throw new NomeInvalidoException();
		}
		if (!RegexMatriculaValidator.isMatriculaValida(usuario.getMatricula())) {
			throw new MatriculaInvalidaException();
		}
	}

	private void adicionarPermissoes(Usuario usuario) {
		List<Permissao> permissoesUsuario = new ArrayList<>();

		if (usuario.getTipoUsuario() == TipoUsuario.ADMIN) {
			Permissao cadastrarUsuario = new Permissao();
			cadastrarUsuario.setCodigo(1L);
			cadastrarUsuario.setDescricao("ROLE_CADASTRAR_USUARIO");
			permissoesUsuario.add(cadastrarUsuario);

			Permissao removerFuncionario = new Permissao();
			removerFuncionario.setCodigo(4L);
			removerFuncionario.setDescricao("ROLE_REMOVER_USUARIO");
			permissoesUsuario.add(removerFuncionario);
		}

		Permissao atualizarUsuario = new Permissao();
		atualizarUsuario.setCodigo(3L);
		atualizarUsuario.setDescricao("ROLE_ATUALIZAR_USUARIO");
		permissoesUsuario.add(atualizarUsuario);

		Permissao pesquisarUsuario = new Permissao();
		pesquisarUsuario.setCodigo(2L);
		pesquisarUsuario.setDescricao("ROLE_PESQUISAR_USUARIO");
		permissoesUsuario.add(pesquisarUsuario);

		usuario.setPermissoes(permissoesUsuario);
	}

}