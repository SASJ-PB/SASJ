package br.edu.ifpb.monteiro.ads.sasj.api.service.util;

import java.util.regex.Pattern;

public class RegexMatriculaValidator {

	private static final Pattern REGEX_NOME = Pattern.compile("^[a-zA-Z]{2}\\-\\d{3,4}$");

	public static boolean isMatriculaValida(String nome) {
		return REGEX_NOME.matcher(nome).matches();
	}

	public static void main(String[] args) {

		System.out.println(isMatriculaValida("TT-123"));
		System.out.println(isMatriculaValida("TT-1234"));

	}

}
