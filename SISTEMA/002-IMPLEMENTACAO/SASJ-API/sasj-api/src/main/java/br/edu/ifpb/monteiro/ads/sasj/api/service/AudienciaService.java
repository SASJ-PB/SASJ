package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Processo;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.AudienciaRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.ProcessoRepository;

@Service
public class AudienciaService {

	@Autowired
	private AudienciaRepository audienciaRepository;

	@Autowired
	private ProcessoRepository processoRepository;

	public Audiencia criar(Audiencia audiencia) {

		Processo processo = processoRepository.findByNumeroProcesso(audiencia.getProcesso().getNumeroProcesso());

		if (processo != null) {
			audiencia.setProcesso(processo);
		}

		Audiencia audienciaSalva = audienciaRepository.save(audiencia);

		return audienciaSalva;
	}

	public List<Audiencia> listar() {
		return audienciaRepository.findAll();
	}

	public Audiencia atualizar(Long codigo, Audiencia audiencia) {
		Audiencia audienciaSalva = buscarUsuarioPeloCodigo(codigo);

		BeanUtils.copyProperties(audiencia, audienciaSalva, "codigo", "processo");

		return audienciaRepository.save(audienciaSalva);
	}

	public Audiencia buscarUsuarioPeloCodigo(Long codigo) {
		Audiencia audiencia = audienciaRepository.findOne(codigo);
		if (audiencia == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return audiencia;
	}

	public void remover(Long codigo) {
		audienciaRepository.delete(codigo);
	}

}