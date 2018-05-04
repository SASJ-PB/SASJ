package br.edu.ifpb.monteiro.ads.sasj.api.security;

import br.edu.ifpb.monteiro.ads.sasj.api.service.token.TokenUtils;

public class GeradorToken {

	public static void main(String[] args) {
		
		System.out.println(TokenUtils.encriptarToken("Jadson Alexandre"+"jadsonalexandreg@gmail.com"));
		System.out.println("jaojdsoj/sdsd//sdasdasd\\asdasd.asdasd".replaceAll("\\.", "").replaceAll("/", "").replaceAll("\\\\", ""));
	}

}
