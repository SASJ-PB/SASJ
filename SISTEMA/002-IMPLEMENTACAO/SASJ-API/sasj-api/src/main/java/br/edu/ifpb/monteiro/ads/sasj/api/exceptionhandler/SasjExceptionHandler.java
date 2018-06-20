package br.edu.ifpb.monteiro.ads.sasj.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.AudienciaComHorarioJaOcupadoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.ConciliacaoComHorarioJaOcupadoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.EmailJaCadastradoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.EmailJaVerificadoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.EmailNaoCadastradoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.FalhaNoEnvioDoEmailException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.MatriculaInvalidaException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.MatriculaJaCadastradaException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.MudancaDeStatusInvalidaException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.NomeInvalidoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.ProcessoDuplicadoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.ProcessoInvalidoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.SessaoJuridicaInvalidaException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.StatusInvalidoParaCadastroException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.TokenInvalidoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.UsuarioInexistenteOuInativoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.UsuarioJaInativoException;

@ControllerAdvice
public class SasjExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = criarListaErros(ex.getBindingResult());

		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("recurso.operacao-nao-permitida", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmailJaCadastradoException.class })
	public ResponseEntity<Object> handleEmailJaCadastradoException(EmailJaCadastradoException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("email.ja-cadastrado-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("email.ja-cadastrado-desenvolvedor", null,
				LocaleContextHolder.getLocale());

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmailNaoCadastradoException.class })
	public ResponseEntity<Object> handleEmailNaoCadastradoException(EmailNaoCadastradoException ex,
			WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("email.nao-cadastrado-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmailJaVerificadoException.class })
	public ResponseEntity<Object> handleEmailJaVerificadoException(EmailJaVerificadoException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("usuario.ja-ativo-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ UsernameNotFoundException.class })
	public ResponseEntity<Object> handleUsernameNotFoundException(EmailNaoCadastradoException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("usuario.inativo-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ TokenInvalidoException.class })
	public ResponseEntity<Object> handleTokenInvalidoException(TokenInvalidoException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("token.invalido-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ UsuarioInexistenteOuInativoException.class })
	public ResponseEntity<Object> handleUsuarioInexistenteOuInativoException(UsuarioInexistenteOuInativoException ex,
			WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("usuario.inexistente-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ NomeInvalidoException.class })
	public ResponseEntity<Object> handleNomeInvalidoException(NomeInvalidoException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("nome.invalido-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("nome.invalido-desenvolvedor", null,
				LocaleContextHolder.getLocale());

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ MatriculaJaCadastradaException.class })
	public ResponseEntity<Object> handleMatriculaJaCadastradaException(MatriculaJaCadastradaException ex,
			WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("matricula.ja-cadastrada-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("matricula.ja-cadastrada-desenvolvedor", null,
				LocaleContextHolder.getLocale());

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ MatriculaInvalidaException.class })
	public ResponseEntity<Object> handleMatriculaInvalidaException(MatriculaInvalidaException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("matricula.invalida-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("matricula.invalida-desenvolvedor", null,
				LocaleContextHolder.getLocale());

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ UsuarioJaInativoException.class })
	public ResponseEntity<Object> handleUsuarioJaInativoException(UsuarioJaInativoException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("usuario.ja.inativo-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("usuario.ja.inativo-desenvolvedor", null,
				LocaleContextHolder.getLocale());

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ PersistentObjectException.class })
	public ResponseEntity<Object> handlePersistentObjectException(PersistentObjectException ex, WebRequest request) {
		String mensagemUsuario = "Ocorreu um erro ao tentar salvar os dados";
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ ProcessoInvalidoException.class })
	public ResponseEntity<Object> handleProcessoInvalidoException(ProcessoInvalidoException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("processo.invalido-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("processo.invalido-desenvolvedor", null,
				LocaleContextHolder.getLocale());

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ SessaoJuridicaInvalidaException.class })
	public ResponseEntity<Object> handleSessaoJuridicaInvalidaException(SessaoJuridicaInvalidaException ex,
			WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("sessao.juridica.invalida-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("sessao.juridica.invalida-desenvolvedor", null,
				LocaleContextHolder.getLocale());

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ ProcessoDuplicadoException.class })
	public ResponseEntity<Object> handleProcessoDuplicadoException(ProcessoDuplicadoException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("processo.duplicado-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("processo.duplicado-desenvolvedor", null,
				LocaleContextHolder.getLocale());

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ ConciliacaoComHorarioJaOcupadoException.class })
	public ResponseEntity<Object> handleConciliacaoComHorarioJaOcupadoException(
			ConciliacaoComHorarioJaOcupadoException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("conciliacao.horario.ocupado-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("conciliacao.horario.ocupado-desenvolvedor", null,
				LocaleContextHolder.getLocale());

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ AudienciaComHorarioJaOcupadoException.class })
	public ResponseEntity<Object> handleAudienciaComHorarioJaOcupadoException(AudienciaComHorarioJaOcupadoException ex,
			WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("audiencia.horario.ocupado-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("audiencia.horario.ocupado-desenvolvedor", null,
				LocaleContextHolder.getLocale());

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ StatusInvalidoParaCadastroException.class })
	public ResponseEntity<Object> handleStatusInvalidoParaCadastroException(StatusInvalidoParaCadastroException ex,
			WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("sessao.juridica.status.invalido-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("sessao.juridica.status.invalido-desenvolvedor", null,
				LocaleContextHolder.getLocale());

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ MudancaDeStatusInvalidaException.class })
	public ResponseEntity<Object> handleMudancaDeStatusInvalidaException(MudancaDeStatusInvalidaException ex,
			WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("sessao.juridica.status.mudanca.invalida-usuario", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("sessao.juridica.status.mudanca.invalida-desenvolvedor",
				null, LocaleContextHolder.getLocale());

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ FalhaNoEnvioDoEmailException.class })
	public ResponseEntity<Object> handleFalhaNoEnvioDoEmailException(FalhaNoEnvioDoEmailException ex,
			WebRequest request) {
		String mensagemUsuario = "Erro ao enviar e-mail para a parte interessada";
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {
		String mensagemUsuario = "Preencha os campos corretamente";
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Erro> criarListaErros(BindingResult bindingResult) {

		List<Erro> erros = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();

			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}

		return erros;
	}

	public static class Erro {

		private String mensagemUsuario;
		private String mensagemDesenvolvedor;

		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public void setMensagemUsuario(String mensagemUsuario) {
			this.mensagemUsuario = mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}

		public void setMensagemDesenvolvedor(String mensagemDesenvolvedor) {
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

	}

}