package br.edu.ifpb.monteiro.ads.sasj.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.monteiro.ads.sasj.api.event.RecursoCriadoEvent;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.service.AudienciaService;

@RestController
@RequestMapping("/audiencias")
public class AudienciaResource {

	@Autowired
	private AudienciaService audienciaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_AUDIENCIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Audiencia> cadastrar(@Valid @RequestBody Audiencia audiencia, HttpServletResponse response) {
		Audiencia audienciaSalva = audienciaService.criar(audiencia);
		publisher.publishEvent(new RecursoCriadoEvent(audienciaSalva, response, audienciaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(audienciaSalva);
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_AUDIENCIA') and #oauth2.hasScope('read')")
	public List<Audiencia> listar() {
		return audienciaService.listar();
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_AUDIENCIA') and #oauth2.hasScope('read')")
	public ResponseEntity<Audiencia> buscarPeloCodigo(@PathVariable Long codigo) {
		Audiencia audiencia = audienciaService.buscarUsuarioPeloCodigo(codigo);
		return audiencia != null ? ResponseEntity.ok(audiencia) : ResponseEntity.notFound().build();
	}

	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_AUDIENCIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Audiencia> atualizar(@PathVariable Long codigo, @Valid @RequestBody Audiencia audiencia) {
		Audiencia audienciaSalva = audienciaService.atualizar(codigo, audiencia);
		return ResponseEntity.ok(audienciaSalva);
	}

	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_AUDIENCIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Audiencia> remover(@PathVariable Long codigo) {
		audienciaService.remover(codigo);
		return ResponseEntity.noContent().build();
	}
}