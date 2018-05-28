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
public class AudienciaService {

	@Autowired
	private AudienciaRepository audienciaRepository;

	@Autowired
	private ConciliacaoRepository conciliacaoRepository;

	@Autowired
	private ProcessoRepository processoRepository;
	
	@Autowired
	private AgendamentoService agendamentoService;

	public Audiencia criar(Audiencia audiencia) {

		if (audiencia.getProcesso().getNumeroProcesso().trim().isEmpty()
				|| audiencia.getProcesso().getNomeDaParte().trim().isEmpty()) {
			throw new ProcessoInvalidoException();
		}

		if (audiencia.getDuracaoEstimada() <= 0 || audiencia.getQuantidadeOitivas() <= 0) {
			throw new SessaoJuridicaInvalidaException();
		}
		
		agendamentoService.validarAgendamento(audiencia);

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
		Audiencia audienciaSalva = buscarAudienciaPeloCodigo(codigo);
		BeanUtils.copyProperties(audiencia, audienciaSalva, "codigo", "processo");

		return audienciaRepository.save(audienciaSalva);
	}

	public Audiencia buscarAudienciaPeloCodigo(Long codigo) {
		Audiencia audiencia = audienciaRepository.findOne(codigo);
		if (audiencia == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return audiencia;
	}

	public void remover(Long codigo) {
		Processo processo = buscarAudienciaPeloCodigo(codigo).getProcesso();

		audienciaRepository.delete(codigo);

		List<Audiencia> audiencias = audienciaRepository.findByProcesso(processo);
		List<Conciliacao> concialicoes = conciliacaoRepository.findByProcesso(processo);

		if (audiencias.size() == 0 && concialicoes.size() == 0) {
			processoRepository.delete(processo.getCodigo());
		}

	}

}