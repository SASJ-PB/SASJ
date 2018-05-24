package br.edu.ifpb.monteiro.ads.sasj.api.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Processo;
import br.edu.ifpb.monteiro.ads.sasj.api.service.ProcessoService;

@RestController
@RequestMapping("/processos")
public class ProcessoResource {

	@Autowired
	private ProcessoService processoService;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PROCESSO') and #oauth2.hasScope('read')")
	public List<Processo> listar() {
		return processoService.listar();
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PROCESSO') and #oauth2.hasScope('read')")
	public ResponseEntity<Processo> buscarPeloCodigo(@PathVariable Long codigo) {
		Processo processo = processoService.buscarProcessoPeloCodigo(codigo);
		return processo != null ? ResponseEntity.ok(processo) : ResponseEntity.notFound().build();
	}

	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_PROCESSO') and #oauth2.hasScope('write')")
	public ResponseEntity<Processo> atualizar(@PathVariable Long codigo, @Valid @RequestBody Processo processo) {
		Processo processoSalvo = processoService.atualizar(codigo, processo);
		return ResponseEntity.ok(processoSalvo);
	}

}