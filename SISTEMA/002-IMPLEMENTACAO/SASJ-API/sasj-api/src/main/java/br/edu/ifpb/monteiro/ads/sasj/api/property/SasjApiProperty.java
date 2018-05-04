package br.edu.ifpb.monteiro.ads.sasj.api.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sasj")
public class SasjApiProperty {

	private final Seguranca seguranca = new Seguranca();
	
	private String originPermitida = "http://localhost:4200";
	
	public String getOriginPermitida() {
		return originPermitida;
	}

	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}

	public Seguranca getSeguranca() {
		return seguranca;
	}

	public static class Seguranca {
		
		private boolean EnableHttps;

		public boolean isEnableHttps() {
			return EnableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			EnableHttps = enableHttps;
		}		
	}
}