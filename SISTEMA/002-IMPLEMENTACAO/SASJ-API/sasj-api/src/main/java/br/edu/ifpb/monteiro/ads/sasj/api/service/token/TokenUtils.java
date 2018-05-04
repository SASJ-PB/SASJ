package br.edu.ifpb.monteiro.ads.sasj.api.service.token;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Author: Ian Gallagher <igallagher@securityinnovation.com>
 *
 * This code utilizes jBCrypt, which you need installed to use.
 * jBCrypt: http://www.mindrot.org/projects/jBCrypt/
 */

public class TokenUtils {

	// Valor de workload que será usado para gerar o hash. 10-31 é um valor válido.
	private static int workload = 12;

	/**
	 * Esse método é usado para gerar uma string que representará o token de verificacao de conta do usuário
	 * Sua string tem comprimento de 60 caracteres
	 * @param token_plaintext string em texto simples fornecido para ser encriptado
	 * @return String - uma string com comprimento de 60 caracteres criptografado pelo bcrypt 
	 */
	public static String encriptarToken(String token_plaintext) {
		String salt = BCrypt.gensalt(workload);
		String tokenEncriptado = BCrypt.hashpw(token_plaintext, salt);		
		
		tokenEncriptado = tokenEncriptado.replaceAll("\\.", "").replaceAll("/", "").replaceAll("\\\\", "");
		
		return tokenEncriptado;
	}
	
	/**
	 * Esse método é usado para verificar um hash calculado a partir de um texto simples
	 * Será comparado o valor de entrada com o hash armazenado no banco de dados
	 * @param token_plaintext token que será fornecido via recurso pelo usuario
	 * @param token_repositorio token que está criptografado no banco de dados 
	 * @return boolean - true se o token do usuario confere com o do banco de dados, falso caso não
	 */
	public static boolean checarTokens(String token_plaintext, String token_repositorio) {
		boolean tokenValido = false;

		if(null == token_repositorio || !token_repositorio.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		tokenValido = BCrypt.checkpw(token_plaintext, token_repositorio);

		return(tokenValido);
	}

}
