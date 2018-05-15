package br.edu.ifpb.monteiro.ads.sasj.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
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

import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.EmailJaCadastradoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.EmailJaVerificadoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.EmailNaoCadastradoException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.MatriculaInvalidaException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.MatriculaJaCadastradaException;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.NomeInvalidoException;
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
	public ResponseEntity<Object> handleMatriculaJaCadastradaException(MatriculaJaCadastradaException ex, WebRequest request) {
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