package br.edu.ifpb.monteiro.ads.sasj.api.repository.audiencia;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.AudienciaFilter;

public interface AudienciaRepositoryQuery {

	public Page<Audiencia> filtrar(AudienciaFilter audienciaFilter, Pageable pageable);

	List<Audiencia> filtrarPorData(AudienciaFilter audienciaFilterData);

}