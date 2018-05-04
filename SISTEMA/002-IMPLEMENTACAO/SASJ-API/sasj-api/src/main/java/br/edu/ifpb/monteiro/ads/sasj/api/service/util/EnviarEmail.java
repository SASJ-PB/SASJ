package br.edu.ifpb.monteiro.ads.sasj.api.service.util;

import java.net.MalformedURLException;

import org.apache.commons.mail.EmailException;

import br.edu.ifpb.monteiro.ads.sasj.api.model.Usuario;
import br.edu.ifpb.monteiro.ads.sasj.api.service.EmailService;

public class EnviarEmail {

	public static void main(String[] args) throws MalformedURLException, EmailException {

		Usuario usuario = new Usuario();
		
		usuario.setEmail("sasj@gmail.com");
		usuario.setNome("André Luis");

		EmailService service = new EmailService();
		service.enviarEmailDeRecuperacaodeDeSenhaUsuario(usuario,
				"$2a$12$EtdVcLbN3QZEn2PyzP52u5nmdjphdRmDN9L2oOIC1qm7FjPIL6vG");

	}
}
