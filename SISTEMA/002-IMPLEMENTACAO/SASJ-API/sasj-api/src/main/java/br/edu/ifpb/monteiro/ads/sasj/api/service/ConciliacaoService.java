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
import br.edu.ifpb.monteiro.ads.sasj.api.repository.filter.ConciliacaoFilter;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.MudancaDeStatusInvalidaException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.ProcessoInvalidoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.SessaoJuridicaInvalidaException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.StatusInvalidoParaCadastroException;

@Service
public class ConciliacaoService {

	@Autowired
	private ConciliacaoRepository conciliacaoRepository;

	@Autowired
	private AudienciaRepository audienciaRepository;

	@Autowired
	private ProcessoRepository processoRepository;

	@Autowired
	private AgendamentoService agendamentoService;
	
	@Autowired
	private NotificacaoService notificacaoService;

	public Conciliacao criar(Conciliacao conciliacao) {

		if (conciliacao.getProcesso().getNumeroProcesso().trim().isEmpty()
				|| conciliacao.getProcesso().getNomeDaParte().trim().isEmpty()) {
			throw new ProcessoInvalidoException();
		}

		if (conciliacao.getDuracaoEstimada() <= 0 || conciliacao.getQuantidadeOitivas() <= 0) {
			throw new SessaoJuridicaInvalidaException();
		}

		if (conciliacao.getStatusAgendamento() != StatusAgendamento.CONFIRMADO) {
			throw new StatusInvalidoParaCadastroException();
		}

		agendamentoService.validarAgendamento(conciliacao);

		Processo processo = processoRepository.findByNumeroProcesso(conciliacao.getProcesso().getNumeroProcesso());

		if (processo != null) {
			conciliacao.setProcesso(processo);
		}
		
		notificacaoService.notificarPartesSobreConfirmacao(conciliacao);
		
		return conciliacaoRepository.save(conciliacao);
	}

	public List<Conciliacao> listar() {
		return conciliacaoRepository.findAll();
	}

	public Page<Conciliacao> filtrar(ConciliacaoFilter conciliacaoFilter, Pageable pageable) {
		return conciliacaoRepository.filtrar(conciliacaoFilter, pageable);
	}

	public Conciliacao atualizar(Long codigo, Conciliacao conciliacao) {
		Conciliacao conciliacaoSalva = buscarConciliacaoPeloCodigo(codigo);

		if (conciliacaoSalva.getStatusAgendamento() == StatusAgendamento.CANCELADO) {
			if (conciliacao.getStatusAgendamento() != StatusAgendamento.CANCELADO) {
				throw new MudancaDeStatusInvalidaException();
			}
		}

		boolean isConciliacaoConfirmada = notificacaoService.isConfirmado(conciliacao, conciliacaoSalva);
		boolean isConciliacaoReagendada = notificacaoService.isReagendado(conciliacao, conciliacaoSalva);
		boolean isConciliacaoAdiada = notificacaoService.isAdiado(conciliacao, conciliacaoSalva);
		boolean isConciliacaoCancelada = notificacaoService.isCancelado(conciliacao, conciliacaoSalva);
		
		BeanUtils.copyProperties(conciliacao, conciliacaoSalva, "codigo", "processo");

		agendamentoService.validarAgendamento(conciliacaoSalva);
		
		notificacaoService.definirNotificacao(conciliacaoSalva, isConciliacaoConfirmada, isConciliacaoReagendada,
				isConciliacaoAdiada, isConciliacaoCancelada);

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