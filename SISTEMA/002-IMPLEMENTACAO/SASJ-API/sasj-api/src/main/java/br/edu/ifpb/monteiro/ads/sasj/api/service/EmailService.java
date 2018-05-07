package br.edu.ifpb.monteiro.ads.sasj.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;

@Service
public class EmailService {

	@Value("${sasj.origin-permitida}")
	private String originPermitida;

	public void enviarEmailDeConfirmacaoDeCadastroDeUsuario(Usuario usuario, String tokenParaEmailDeVerificacao)
			throws EmailException, MalformedURLException {
		HtmlEmail email = construirEmail();
		email.setSubject("Confirmação de Cadastro");
		email.addTo(usuario.getEmail());

		email.setHtmlMsg(converterHtmlEmString("confirmacao_cadastro")
				.replaceAll("NOMEDOUSUARIO", usuario.getNome()).replace("LINKDEVERIFICACAO",
						originPermitida + "/verificacao/cadastro/" + tokenParaEmailDeVerificacao));

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

	private HtmlEmail construirEmail() throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.zoho.com");
		email.setSmtpPort(587);
		// TODO: Criar e-mail "sasj.desenvolvimento@hotmail.com"
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
