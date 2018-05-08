package br.edu.ifpb.monteiro.ads.sasj.api.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;
import br.edu.ifpb.monteiro.ads.sasj.api.service.TokenRecuperacaoService;

@RestController
@RequestMapping("/recuperacao")
public class RecuperacaoResource {

	@Autowired
	private TokenRecuperacaoService tokenRecuperacaoService;
	
	@PutMapping("/senha")
	public void recuperarSenhaUsuario(String email) {
		try {
			tokenRecuperacaoService.recuperacaoSenha(email);
		} catch (Exception e) {
			throw e;
		}		
	}
	
	@GetMapping("/{token}")
	public ResponseEntity<Usuario> buscarUsuarioPorToken(@PathVariable String token) {
		Usuario usuario = tokenRecuperacaoService.buscarUsuarioPorToken(token);
		return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();	
	}
	
	@PutMapping("/usuario/{codigo}")	
	public ResponseEntity<Usuario> atualizarSenha(@PathVariable Long codigo,@Valid @RequestBody Usuario usuario) {
		try {
			Usuario usuarioSalvo = tokenRecuperacaoService.atualizarSenha(codigo, usuario);
			return ResponseEntity.ok(usuarioSalvo);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

}