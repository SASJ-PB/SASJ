package br.edu.ifpb.monteiro.ads.sasj.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Processo;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

	public Processo findByNumeroProcesso(String numeroProcesso);

	public Processo findByNomeDaParte(String nomeDaParte);

}