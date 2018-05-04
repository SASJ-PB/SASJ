package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.net.MalformedURLException;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.model.TokenRecuperacao;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.TokenRecuperacaoRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.UsuarioRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.EmailNaoCadastradoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.TokenInvalidoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.token.TokenUtils;

@Service
public class TokenRecuperacaoService {

	@Autowired
	private TokenRecuperacaoRepository tokenRecuperacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EmailService emailService;

	public void recuperacaoSenha(String email) {
		Usuario usuarioRecuperarSenha = usuarioRepository.findByEmail(email);
		if (usuarioRecuperarSenha != null) {
			String token = criarTokenRecuperacao(usuarioRecuperarSenha);
			try {
				emailService.enviarEmailDeRecuperacaodeDeSenhaUsuario(usuarioRecuperarSenha, token);
			} catch (MalformedURLException | EmailException e) {
				System.out.println(e.getMessage());
			}
		} else {
			throw new EmailNaoCadastradoException();
		}

	}

	public String criarTokenRecuperacao(Usuario usuario) {
		TokenRecuperacao tokenRecuperacaoBusca = tokenRecuperacaoRepository.findByUsuario(usuario);

		if (tokenRecuperacaoBusca == null) {
			TokenRecuperacao tokenRecuperacao = new TokenRecuperacao();
			tokenRecuperacao.setToken(TokenUtils.encriptarToken(usuario.getNome() + usuario.getEmail()));
			tokenRecuperacao.setUsuario(usuario);
			tokenRecuperacaoRepository.save(tokenRecuperacao);

			return tokenRecuperacao.getToken();
		}
		return tokenRecuperacaoBusca.getToken();
	}

	public Usuario buscarUsuarioPorToken(String token) {
		TokenRecuperacao tokenRecuperacaoBusca = tokenRecuperacaoRepository.findByToken(token);

		if (tokenRecuperacaoBusca != null) {
			return tokenRecuperacaoBusca.getUsuario();
		}
		throw new TokenInvalidoException();
	}

	public Usuario atualizarSenha(Long codigo, Usuario usuario) {
		Usuario usuarioSalvo = usuarioRepository.findOne(codigo);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		usuarioSalvo.setSenha(encoder.encode(usuario.getSenha()));

		apagarToken(usuario);

		return usuarioRepository.save(usuarioSalvo);
	}

	private void apagarToken(Usuario usuario) {
		TokenRecuperacao tokenRecuperacaoBusca = tokenRecuperacaoRepository.findByUsuario(usuario);
		tokenRecuperacaoRepository.delete(tokenRecuperacaoBusca);
	}
}
