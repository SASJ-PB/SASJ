package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.net.MalformedURLException;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.model.TokenVerificacao;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.TokenVerificacaoRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.UsuarioRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.EmailJaVerificadoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.EmailNaoCadastradoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.TokenInvalidoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.token.TokenUtils;

@Service
public class TokenVerificacaoService {

	@Autowired
	private TokenVerificacaoRepository tokenVerificacaoRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EmailService emailService;

	public String criarTokenVerificacao(Usuario usuario) {

		TokenVerificacao tokenVerificacaoBusca = tokenVerificacaoRepository.findByUsuario(usuario);

		if (tokenVerificacaoBusca == null) {
			TokenVerificacao tokenVerificacao = new TokenVerificacao();
			tokenVerificacao.setToken(TokenUtils.encriptarToken(usuario.getNome() + usuario.getEmail()));
			tokenVerificacao.setUsuario(usuario);
			tokenVerificacaoRepository.save(tokenVerificacao);

			return tokenVerificacao.getToken();
		}

		return tokenVerificacaoBusca.getToken();
	}

	public void ativarUsarioPorToken(String token) {
		TokenVerificacao tokenVerificacao = buscarTokenVerificacao(token);
		if (tokenVerificacao.getUsuario().isAtivo()) {
			throw new EmailJaVerificadoException();
		}
		usuarioService.atualizarPropriedadeAtivo(tokenVerificacao.getUsuario().getCodigo(), true);
		tokenVerificacaoRepository.delete(tokenVerificacao);
	}

	private TokenVerificacao buscarTokenVerificacao(String token) {
		TokenVerificacao tokenVerificacao = tokenVerificacaoRepository.findByToken(token);
		if (tokenVerificacao == null) {
			throw new TokenInvalidoException();
		}
		return tokenVerificacao;
	}

	public void reenvioEmailConfirmacao(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		TokenVerificacao token = tokenVerificacaoRepository.findByUsuario(usuario);

		if (usuario == null) {
			throw new EmailNaoCadastradoException();
		}

		if (usuario.isAtivo()) {
			throw new EmailJaVerificadoException();
		}

		if (token != null) {

			try {
				emailService.enviarEmailDeConfirmacaoDeCadastroDeUsuario(usuario, token.getToken());
			} catch (MalformedURLException | EmailException e) {
				e.printStackTrace();
			}
		}

	}
}
