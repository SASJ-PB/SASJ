package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.model.ParteInteressada;
import br.edu.ifpb.monteiro.ads.sasj.api.model.SessaoJuridica;
import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;
import br.edu.ifpb.monteiro.ads.sasj.api.service.exception.FalhaNoEnvioDoEmailException;

@Service
public class EmailService {

	@Value("${sasj.origin-permitida}")
	private String originPermitida;

	public void enviarEmailDeConfirmacaoDeCadastroDeUsuario(Usuario usuario, String tokenParaEmailDeVerificacao)
			throws EmailException, MalformedURLException {
		HtmlEmail email = construirEmail();
		email.setSubject("Confirmação de Cadastro");
		email.addTo(usuario.getEmail());

		email.setHtmlMsg(
				converterHtmlEmString("confirmacao_cadastro").replaceAll("NOMEDOUSUARIO", usuario.getNome()).replace(
						"LINKDEVERIFICACAO", originPermitida + "/verificacao/cadastro/" + tokenParaEmailDeVerificacao));

		email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
		email.send();

	}

	public void enviarEmailDeRecuperacaodeDeSenhaUsuario(Usuario usuario, String tokenParaEmailDeRecuperacao)
			throws EmailException, MalformedURLException {
		HtmlEmail email = construirEmail();
		email.setSubject("Recuperacão de senha");
		email.addTo(usuario.getEmail());

		email.setHtmlMsg(converterHtmlEmString("recuperacao_senha").replaceAll("NOMEDOUSUARIO", usuario.getNome())
				.replace("LINKDERECUPERACAO", originPermitida + "/recuperacao/senha/" + tokenParaEmailDeRecuperacao));

		email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
		email.send();
	}

	public void enviarEmailDeRedefinicaoDeSenhaUsuario(Usuario usuario, String tokenParaEmailDeRedefinicao)
			throws EmailException, MalformedURLException {
		HtmlEmail email = construirEmail();
		email.setSubject("Configuração de conta");
		email.addTo(usuario.getEmail());

		email.setHtmlMsg(converterHtmlEmString("usuario_redefinicao_senha")
				.replaceAll("NOMEDOUSUARIO", usuario.getNome())
				.replace("LINKDEREDEFINICAO", originPermitida + "/recuperacao/senha/" + tokenParaEmailDeRedefinicao));

		email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
		email.send();
	}

	public void enviarEmailLembreteDeAudienciaConfirmada(SessaoJuridica sessaoJuridica) {
		try {

			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String dataHoraSessao = "";
			List<ParteInteressada> partesInteressadas = sessaoJuridica.getPartesInteressadas();

			for (ParteInteressada parteInteressada : partesInteressadas) {
				HtmlEmail email = construirEmail();
				email.setSubject("Lembrete de confirmação de audiência");

				email.addTo(parteInteressada.getEmail());

				dataHoraSessao = sessaoJuridica.getAgendamento().format(pattern);

				email.setHtmlMsg(converterHtmlEmString("lembrete_audiencia_confirmada")
						.replaceAll("NOMEDAPARTEINTERESSADA", parteInteressada.getNome())
						.replace("DATAHORASESSAO", dataHoraSessao)
						.replace("FUNCAODAPARTEINTERESSADA", parteInteressada.getFuncao())
						.replace("SATATUSSESSAO", sessaoJuridica.getStatusAgendamento().toString()));

				email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
				email.send();
			}
		} catch (EmailException ex) {
			throw new FalhaNoEnvioDoEmailException(ex.getCause());
		}
	}
	
	public void enviarEmailLembreteDeConciliacaoConfirmada(SessaoJuridica sessaoJuridica) {
		try {
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String dataHoraSessao = "";
			List<ParteInteressada> partesInteressadas = sessaoJuridica.getPartesInteressadas();

			for (ParteInteressada parteInteressada : partesInteressadas) {
				HtmlEmail email = construirEmail();
				email.setSubject("Lembrete de confirmação de conciliação");

				email.addTo(parteInteressada.getEmail());

				dataHoraSessao = sessaoJuridica.getAgendamento().format(pattern);

				email.setHtmlMsg(converterHtmlEmString("lembrete_conciliacao_confirmada")
						.replaceAll("NOMEDAPARTEINTERESSADA", parteInteressada.getNome())
						.replace("DATAHORASESSAO", dataHoraSessao)
						.replace("FUNCAODAPARTEINTERESSADA", parteInteressada.getFuncao())
						.replace("SATATUSSESSAO", sessaoJuridica.getStatusAgendamento().toString()));

				email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
				email.send();
			}
		} catch (EmailException ex) {
			throw new FalhaNoEnvioDoEmailException(ex.getCause());
		}
	}

	public void enviarEmailLembreteDeAudienciaReagendada(SessaoJuridica sessaoJuridica) {
		try {
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String dataHoraSessao = "";
			List<ParteInteressada> partesInteressadas = sessaoJuridica.getPartesInteressadas();

			for (ParteInteressada parteInteressada : partesInteressadas) {
				HtmlEmail email = construirEmail();
				email.setSubject("Lembrete de reagendamento de audiência");

				email.addTo(parteInteressada.getEmail());

				dataHoraSessao = sessaoJuridica.getAgendamento().format(pattern);

				email.setHtmlMsg(converterHtmlEmString("lembrete_audiencia_reagendada")
						.replaceAll("NOMEDAPARTEINTERESSADA", parteInteressada.getNome())
						.replace("DATAHORASESSAO", dataHoraSessao)
						.replace("FUNCAODAPARTEINTERESSADA", parteInteressada.getFuncao())
						.replace("STATUSSESSAO", sessaoJuridica.getStatusAgendamento().toString()));

				email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
				email.send();
			}
		} catch (EmailException ex) {
			throw new FalhaNoEnvioDoEmailException(ex.getCause());
		}
	}
	
	public void enviarEmailLembreteDeConciliacaoReagendada(SessaoJuridica sessaoJuridica) {
		try {
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String dataHoraSessao = "";
			List<ParteInteressada> partesInteressadas = sessaoJuridica.getPartesInteressadas();

			for (ParteInteressada parteInteressada : partesInteressadas) {
				HtmlEmail email = construirEmail();
				email.setSubject("Lembrete de reagendamento de conciliação");

				email.addTo(parteInteressada.getEmail());

				dataHoraSessao = sessaoJuridica.getAgendamento().format(pattern);

				email.setHtmlMsg(converterHtmlEmString("lembrete_conciliacao_reagendada")
						.replaceAll("NOMEDAPARTEINTERESSADA", parteInteressada.getNome())
						.replace("DATAHORASESSAO", dataHoraSessao)
						.replace("FUNCAODAPARTEINTERESSADA", parteInteressada.getFuncao())
						.replace("STATUSSESSAO", sessaoJuridica.getStatusAgendamento().toString()));

				email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
				email.send();
			}
		} catch (EmailException ex) {
			throw new FalhaNoEnvioDoEmailException(ex.getCause());
		}
	}
	
	public void enviarEmailLembreteDeAudienciaAdiada(SessaoJuridica sessaoJuridica) {
		try {
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String dataHoraSessao = "";
			List<ParteInteressada> partesInteressadas = sessaoJuridica.getPartesInteressadas();

			for (ParteInteressada parteInteressada : partesInteressadas) {
				HtmlEmail email = construirEmail();
				email.setSubject("Lembrete de adiamento de audiência");

				email.addTo(parteInteressada.getEmail());

				dataHoraSessao = sessaoJuridica.getAgendamento().format(pattern);

				email.setHtmlMsg(converterHtmlEmString("lembrete_audiencia_adiada")
						.replaceAll("NOMEDAPARTEINTERESSADA", parteInteressada.getNome())
						.replace("DATAHORASESSAO", dataHoraSessao)
						.replace("FUNCAODAPARTEINTERESSADA", parteInteressada.getFuncao())
						.replace("STATUSSESSAO", sessaoJuridica.getStatusAgendamento().toString()));

				email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
				email.send();
			}
		} catch (EmailException ex) {
			throw new FalhaNoEnvioDoEmailException(ex.getCause());
		}
	}

	public void enviarEmailLembreteDeConciliacaoAdiada(SessaoJuridica sessaoJuridica) {
		try {
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String dataHoraSessao = "";
			List<ParteInteressada> partesInteressadas = sessaoJuridica.getPartesInteressadas();

			for (ParteInteressada parteInteressada : partesInteressadas) {
				HtmlEmail email = construirEmail();
				email.setSubject("Lembrete de adiamento de conciliação");

				email.addTo(parteInteressada.getEmail());

				dataHoraSessao = sessaoJuridica.getAgendamento().format(pattern);

				email.setHtmlMsg(converterHtmlEmString("lembrete_conciliacao_adiada")
						.replaceAll("NOMEDAPARTEINTERESSADA", parteInteressada.getNome())
						.replace("DATAHORASESSAO", dataHoraSessao)
						.replace("FUNCAODAPARTEINTERESSADA", parteInteressada.getFuncao())
						.replace("STATUSSESSAO", sessaoJuridica.getStatusAgendamento().toString()));

				email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
				email.send();
			}
		} catch (EmailException ex) {
			throw new FalhaNoEnvioDoEmailException(ex.getCause());
		}
	}
	
	public void enviarEmailLembreteDeAudienciaCancelada(SessaoJuridica sessaoJuridica) {
		try {
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String dataHoraSessao = "";
			List<ParteInteressada> partesInteressadas = sessaoJuridica.getPartesInteressadas();

			for (ParteInteressada parteInteressada : partesInteressadas) {
				HtmlEmail email = construirEmail();
				email.setSubject("Lembrete de cancelamento de audiência");

				email.addTo(parteInteressada.getEmail());

				dataHoraSessao = sessaoJuridica.getAgendamento().format(pattern);

				email.setHtmlMsg(converterHtmlEmString("lembrete_audiencia_cancelada")
						.replaceAll("NOMEDAPARTEINTERESSADA", parteInteressada.getNome())
						.replace("DATAHORASESSAO", dataHoraSessao)
						.replace("FUNCAODAPARTEINTERESSADA", parteInteressada.getFuncao())
						.replace("STATUSSESSAO", sessaoJuridica.getStatusAgendamento().toString()));

				email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
				email.send();
			}
		} catch (EmailException ex) {
			throw new FalhaNoEnvioDoEmailException(ex.getCause());
		}
	}

	public void enviarEmailLembreteDeConciliacaoCancelada(SessaoJuridica sessaoJuridica) {
		try {
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String dataHoraSessao = "";
			List<ParteInteressada> partesInteressadas = sessaoJuridica.getPartesInteressadas();

			for (ParteInteressada parteInteressada : partesInteressadas) {
				HtmlEmail email = construirEmail();
				email.setSubject("Lembrete de cancelamento de conciliação");

				email.addTo(parteInteressada.getEmail());

				dataHoraSessao = sessaoJuridica.getAgendamento().format(pattern);

				email.setHtmlMsg(converterHtmlEmString("lembrete_conciliacao_cancelada")
						.replaceAll("NOMEDAPARTEINTERESSADA", parteInteressada.getNome())
						.replace("DATAHORASESSAO", dataHoraSessao)
						.replace("FUNCAODAPARTEINTERESSADA", parteInteressada.getFuncao())
						.replace("STATUSSESSAO", sessaoJuridica.getStatusAgendamento().toString()));

				email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
				email.send();
			}
		} catch (EmailException ex) {
			throw new FalhaNoEnvioDoEmailException(ex.getCause());
		}
	}
	
	private HtmlEmail construirEmail() throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.zoho.com");
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator("sasj.desenvolvimento@zoho.com", "123456789"));
		email.setStartTLSEnabled(true);
		email.setFrom("sasj.desenvolvimento@zoho.com", "Sistema de Alocação de Sessões Jurídicas");
		email.setContent(null, "text/html; charset=iso-8859-1");
		return email;
	}

	private String converterHtmlEmString(String nomeArquivo) {
		StringBuilder contentBuilder = new StringBuilder();
		try {
			InputStream is = getClass().getResourceAsStream("/template/email/" + nomeArquivo + ".html");
			BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
			System.out.println("ERRO DE CONVERSÃO: " + e.getMessage());
		}
		return contentBuilder.toString();
	}

}