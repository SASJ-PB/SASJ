package br.edu.ifpb.monteiro.ads.sasj.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.monteiro.ads.sasj.api.service.TokenVerificacaoService;

@RestController
@RequestMapping("/verificacao")
public class VerificacaoResource {
	
	@Autowired
	private TokenVerificacaoService tokenVerificacaoService;   
	
	@PutMapping("/cadastro/{token}")
	public void verificarCadastroUsuario(@PathVariable String token) {
		try {
			tokenVerificacaoService.ativarUsarioPorToken(token);
		} catch (Exception e) {
			throw e;
		}		
	}
	
	@PutMapping("/reenvio")
	public void recuperarAcessoUsuario(String email) {		
		tokenVerificacaoService.reenvioEmailConfirmacao(email);	
	}

}