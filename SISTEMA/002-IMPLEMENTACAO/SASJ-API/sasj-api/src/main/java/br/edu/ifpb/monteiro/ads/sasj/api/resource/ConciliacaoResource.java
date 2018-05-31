package br.edu.ifpb.monteiro.ads.sasj.api.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import br.edu.ifpb.monteiro.ads.sasj.api.model.Conciliacao;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.ConciliacaoFilter;
import br.edu.ifpb.monteiro.ads.sasj.api.service.ConciliacaoService;

@RestController
@RequestMapping("/conciliacoes")
public class ConciliacaoResource {

	@Autowired
	private ConciliacaoService conciliacaoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CONCILIACAO') and #oauth2.hasScope('write')")
	public ResponseEntity<Conciliacao> cadastrar(@Valid @RequestBody Conciliacao conciliacao,
			HttpServletResponse response) {
		Conciliacao conciliacaoSalva = conciliacaoService.criar(conciliacao);
		publisher.publishEvent(new RecursoCriadoEvent(conciliacaoSalva, response, conciliacaoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(conciliacaoSalva);
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CONCILIACAO') and #oauth2.hasScope('read')")
	public Page<Conciliacao> pesquisar(ConciliacaoFilter concialiacaoFilter, Pageable pageable) {
		return conciliacaoService.filtrar(concialiacaoFilter, pageable);
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CONCILIACAO') and #oauth2.hasScope('read')")
	public ResponseEntity<Conciliacao> buscarPeloCodigo(@PathVariable Long codigo) {
		Conciliacao conciliacao = conciliacaoService.buscarConciliacaoPeloCodigo(codigo);
		return conciliacao != null ? ResponseEntity.ok(conciliacao) : ResponseEntity.notFound().build();
	}

	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_CONCILIACAO') and #oauth2.hasScope('write')")
	public ResponseEntity<Conciliacao> atualizar(@PathVariable Long codigo,
			@Valid @RequestBody Conciliacao conciliacao) {
		Conciliacao audienciaSalva = conciliacaoService.atualizar(codigo, conciliacao);
		return ResponseEntity.ok(audienciaSalva);
	}

	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CONCILIACAO') and #oauth2.hasScope('write')")
	public ResponseEntity<Audiencia> remover(@PathVariable Long codigo) {
		conciliacaoService.remover(codigo);
		return ResponseEntity.noContent().build();
	}

}