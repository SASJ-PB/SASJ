package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Processo;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.ProcessoRepository;

@Service
public class ProcessoService {

	@Autowired
	private ProcessoRepository processoRepository;

	public List<Processo> listar() {
		return processoRepository.findAll();
	}

	public Processo atualizar(Long codigo, Processo processo) {
		Processo processoSalvo = buscarProcessoPeloCodigo(codigo);

		BeanUtils.copyProperties(processo, processoSalvo, "codigo");

		return processoRepository.save(processoSalvo);
	}

	public Processo buscarProcessoPeloCodigo(Long codigo) {
		Processo processo = processoRepository.findOne(codigo);
		if (processo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return processo;
	}

}