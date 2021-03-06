package br.edu.ifpb.monteiro.ads.sasj.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.TipoAudiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Processo;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.audiencia.AudienciaRepositoryQuery;

public interface AudienciaRepository extends JpaRepository<Audiencia, Long>, AudienciaRepositoryQuery {

	public List<Audiencia> findByTipoAudiencia(TipoAudiencia tipoAudiencia);
	
	public List<Audiencia> findByProcesso(Processo processo);

}