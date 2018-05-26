package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Conciliacao;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Processo;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.AudienciaRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.ConciliacaoRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.ProcessoRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.ProcessoInvalidoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.SessaoJuridicaInvalidaException;

@Service
public class ConciliacaoService {

	@Autowired
	private ConciliacaoRepository conciliacaoRepository;
	
	@Autowired
	private AudienciaRepository audienciaRepository;

	@Autowired
	private ProcessoRepository processoRepository;

	public Conciliacao criar(Conciliacao conciliacao) {

		if (conciliacao.getProcesso().getNumeroProcesso().trim().isEmpty()
				|| conciliacao.getProcesso().getNomeDaParte().trim().isEmpty()) {
			throw new ProcessoInvalidoException();
		}
		
		if(conciliacao.getDuracaoEstimada() <= 0 || conciliacao.getQuantidadeOitivas() <=0) {
			throw new SessaoJuridicaInvalidaException();
		}
		
		Processo processo = processoRepository.findByNumeroProcesso(conciliacao.getProcesso().getNumeroProcesso());

		if (processo != null) {
			conciliacao.setProcesso(processo);
		}

		Conciliacao conciliacaoSalva = conciliacaoRepository.save(conciliacao);

		return conciliacaoSalva;
	}

	public List<Conciliacao> listar() {
		return conciliacaoRepository.findAll();
	}

	public Conciliacao atualizar(Long codigo, Conciliacao conciliacao) {
		Conciliacao conciliacaoSalva = buscarConciliacaoPeloCodigo(codigo);
		BeanUtils.copyProperties(conciliacao, conciliacaoSalva, "codigo", "processo");
		
		return conciliacaoRepository.save(conciliacaoSalva);
	}

	public Conciliacao buscarConciliacaoPeloCodigo(Long codigo) {
		Conciliacao conciliacao = conciliacaoRepository.findOne(codigo);
		if (conciliacao == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return conciliacao;
	}

	public void remover(Long codigo) {
		Processo processo = buscarConciliacaoPeloCodigo(codigo).getProcesso();

		conciliacaoRepository.delete(codigo);

		List<Audiencia> audiencias = audienciaRepository.findByProcesso(processo);
		List<Conciliacao> concialicoes = conciliacaoRepository.findByProcesso(processo);

		if (audiencias.size() == 0 && concialicoes.size() == 0) {
			processoRepository.delete(processo.getCodigo());
		}
		
	}

}