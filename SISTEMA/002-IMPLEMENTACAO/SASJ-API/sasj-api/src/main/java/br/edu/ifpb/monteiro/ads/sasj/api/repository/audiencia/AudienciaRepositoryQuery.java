package br.edu.ifpb.monteiro.ads.sasj.api.repository.audiencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.AudienciaFilter;

public interface AudienciaRepositoryQuery {

	public Page<Audiencia> filtrar(AudienciaFilter audienciaFilter, Pageable pageable);

}