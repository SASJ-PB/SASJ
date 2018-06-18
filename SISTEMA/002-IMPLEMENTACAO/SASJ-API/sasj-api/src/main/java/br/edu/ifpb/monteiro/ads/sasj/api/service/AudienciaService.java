package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.enums.StatusAgendamento;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Audiencia;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Conciliacao;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Processo;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.AudienciaRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.ConciliacaoRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.ProcessoRepository;
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.AudienciaFilter;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.MudancaDeStatusInvalidaException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.ProcessoInvalidoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.SessaoJuridicaInvalidaException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.StatusInvalidoParaCadastroException;

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

	@Autowired
	private NotificacaoService notificacaoService;

	public Audiencia criar(Audiencia audiencia) {

		if (audiencia.getProcesso().getNumeroProcesso().trim().isEmpty()
				|| audiencia.getProcesso().getNomeDaParte().trim().isEmpty()) {
			throw new ProcessoInvalidoException();
		}

		if (audiencia.getDuracaoEstimada() <= 0 || audiencia.getQuantidadeOitivas() <= 0) {
			throw new SessaoJuridicaInvalidaException();
		}

		if (audiencia.getStatusAgendamento() != StatusAgendamento.CONFIRMADO) {
			throw new StatusInvalidoParaCadastroException();
		}

		agendamentoService.validarAgendamento(audiencia);

		Processo processo = processoRepository.findByNumeroProcesso(audiencia.getProcesso().getNumeroProcesso());

		if (processo != null) {
			audiencia.setProcesso(processo);
		}

		notificacaoService.notificarPartesSobreConfirmacao(audiencia);

		return audienciaRepository.save(audiencia);

	}

	public List<Audiencia> listar() {
		return audienciaRepository.findAll();
	}

	public Page<Audiencia> filtrar(AudienciaFilter lancamentoFilter, Pageable pageable) {
		return audienciaRepository.filtrar(lancamentoFilter, pageable);
	}

	public Audiencia atualizar(Long codigo, Audiencia audiencia) {
		Audiencia audienciaSalva = buscarAudienciaPeloCodigo(codigo);

		if (audienciaSalva.getStatusAgendamento() == StatusAgendamento.CANCELADO) {
			if (audiencia.getStatusAgendamento() != StatusAgendamento.CANCELADO) {
				throw new MudancaDeStatusInvalidaException();
			}
		}

		boolean isAudienciaConfirmada = notificacaoService.isConfirmado(audiencia, audienciaSalva);
		boolean isAudienciaReagendada = notificacaoService.isReagendado(audiencia, audienciaSalva);
		boolean isAudienciaAdiada = notificacaoService.isAdiado(audiencia, audienciaSalva);
		boolean isAudienciaCancelada = notificacaoService.isCancelado(audiencia, audienciaSalva);

		BeanUtils.copyProperties(audiencia, audienciaSalva, "codigo", "processo");

		agendamentoService.validarAgendamento(audienciaSalva);

		notificacaoService.definirNotificacao(audienciaSalva, isAudienciaConfirmada, isAudienciaReagendada,
				isAudienciaAdiada, isAudienciaCancelada);

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