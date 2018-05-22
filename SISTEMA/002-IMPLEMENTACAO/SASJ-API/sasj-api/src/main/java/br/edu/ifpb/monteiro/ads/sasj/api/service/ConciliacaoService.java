package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Conciliacao;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Processo;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.ConciliacaoRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.ProcessoRepository;

@Service
public class ConciliacaoService {

	@Autowired
	private ConciliacaoRepository conciliacaoRepository;

	@Autowired
	private ProcessoRepository processoRepository;

	public Conciliacao criar(Conciliacao conciliacao) {

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

		BeanUtils.copyProperties(conciliacao, conciliacaoSalva, "codigo");
		
		Processo processo = processoRepository.findByNumeroProcesso(conciliacao.getProcesso().getNumeroProcesso());

		if (processo != null) {
			conciliacao.setProcesso(processo);
		}

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
		conciliacaoRepository.delete(codigo);
	}

}